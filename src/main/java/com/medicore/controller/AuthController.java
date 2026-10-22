package com.medicore.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.entity.User;
import com.medicore.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/sign-up")
	public String SignUp(@RequestBody User newUser) {
		return authService.register(newUser);
	}
	

	@PostMapping("/sign-in")
	public String SignIn(@RequestBody User reqUser) {
		return authService.login(reqUser);
	}
	
}
