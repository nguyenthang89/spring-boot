package com.yuen.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	DataRepository dataRepository;
	
	@GetMapping("/user")
	public String getAllDataToPaging(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5000") Integer size
			/*@RequestParam(name = "sort", required = false, defaultValue = "desc") Sort sort*/) {
		
		Pageable pageable = new PageRequest(page, size);
		model.addAttribute("data", dataRepository.findAll(pageable));
		return "user/tables";
	}
	
	
}
