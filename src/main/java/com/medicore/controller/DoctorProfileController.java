package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
import com.medicore.service.DoctorProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctor")
public class DoctorProfileController {

	private final DoctorProfileService profileService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getProfiles(){
		return profileService.getAllprofiles();
	}
}
