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

import com.yuen.domain.web.CustomUserDetails;
import com.yuen.domain.web.User;
import com.yuen.jwt.JwtTokenProvider;
import com.yuen.payload.LoginResponse;

@RestController
public class ApiUserController {

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
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}

	/*
	 * @PostMapping(value = "/api/v1/register") public ResponseEntity<Boolean>
	 * register(@RequestBody com.yuen.domain.api.User user) { User dbUser = new
	 * User(); dbUser.setName(user.getName()); dbUser.setEmail(user.getEmail());
	 * dbUser.setPassword(user.getPassword());
	 * dbUser.addRole(roleService.findByName("ROLE_CUSTOMER"));
	 * 
	 * return new ResponseEntity<Boolean>(userService.register(dbUser),
	 * HttpStatus.CREATED); }
	 */

}
