package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.DoctorProfileRequestDto;
import com.medicore.dto.PatientProfileRequestDto;
import com.medicore.entity.PatientProfile;
import com.medicore.service.DoctorProfileService;
import com.medicore.service.PatientProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final DoctorProfileService doctorProfileService;
	private final PatientProfileService patientProfileService;
	
	// doctor's api's
	
	@GetMapping("/doctor")
	public ResponseEntity<ApiResponseDto> getDoctorProfiles(){
		return doctorProfileService.getAllprofiles();
	}

	@GetMapping("/doctor/{id}")
	public ResponseEntity<ApiResponseDto> getDoctorProfile(@PathVariable Long id){
		return doctorProfileService.getUserProfileById(id);
	}

	@PutMapping("/doctor/{id}")
	public ResponseEntity<ApiResponseDto> updateDoctorProfile(@PathVariable Long id, @RequestBody DoctorProfileRequestDto reqProfile){
		return doctorProfileService.updateProfile(id, reqProfile);
	}
	
	
	

	// patient's api's
	
	@GetMapping("/patient")
	public ResponseEntity<ApiResponseDto> getPatientProfiles(){
		return patientProfileService.getAllProfiles();
	}
	

	@GetMapping("/patient/{id}")
	public ResponseEntity<ApiResponseDto> getPatientProfile(@PathVariable Long id){
		return patientProfileService.getUserProfileById(id);
	}
	
	@PutMapping("/patient/{id}")
	public ResponseEntity<ApiResponseDto> updatePatientProfile(@PathVariable Long id, @RequestBody PatientProfileRequestDto reqProfile){
		return patientProfileService.updateProfile(id, reqProfile);
	}
}
