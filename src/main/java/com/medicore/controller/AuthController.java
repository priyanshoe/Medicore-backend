package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.DoctorSignUpRequestDto;
import com.medicore.dto.PatientSignUpRequestDto;
import com.medicore.dto.UserRequestDto;
import com.medicore.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/sign-up/doctor")
	public ResponseEntity<ApiResponseDto> SignUp(@RequestBody DoctorSignUpRequestDto signUpRequestDto) {
		return authService.register(signUpRequestDto.getUser(), signUpRequestDto.getProfile());
	}
	
	@PostMapping("/sign-up/patient")
	public ResponseEntity<ApiResponseDto> SignUp(@RequestBody PatientSignUpRequestDto signUpRequestDto) {
		return authService.register(signUpRequestDto.getUser(), signUpRequestDto.getProfile());
	}

	@PostMapping("/sign-up/admin")
	public ResponseEntity<ApiResponseDto> SignUp(@RequestBody UserRequestDto newAdmin) {
		return authService.register(newAdmin);
	}

	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponseDto> SignIn(@RequestBody UserRequestDto reqUser) {
		return authService.login(reqUser);
	}
	
}
