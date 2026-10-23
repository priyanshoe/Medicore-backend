package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.DoctorProfileRequestDto;
import com.medicore.dto.UserRequestDto;
import com.medicore.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponseDto> SignUp(@RequestBody UserRequestDto newUser, @RequestBody DoctorProfileRequestDto newDoctorProfile) {
		return authService.register(newUser,newDoctorProfile);
	}
	

	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponseDto> SignIn(@RequestBody UserRequestDto reqUser) {
		return authService.login(reqUser);
	}
	
}
