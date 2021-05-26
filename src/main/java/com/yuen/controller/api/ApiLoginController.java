package com.yuen.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuen.domain.web.MyUserDetails;
import com.yuen.domain.web.User;
import com.yuen.jwt.JwtTokenProvider;
import com.yuen.payload.LoginResponse;

@RestController
public class ApiLoginController {

	// @Autowired
	// private PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping(value = "/api/login")
	public LoginResponse login(@Valid @RequestBody User loginRequest) {
		// Xác thực từ username và password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		// Nếu không xảy ra exception tức là thông tin hợp lệ
		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Trả về jwt cho người dùng.
		String jwt = tokenProvider.generateToken((MyUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}
	
}
