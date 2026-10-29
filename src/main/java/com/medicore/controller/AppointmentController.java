package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.ENUM.AppointmentStatus;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.AppointmentRequestDto;
import com.medicore.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	private final AppointmentService appointmentService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> allAppointments(){
		return appointmentService.getAll();
	}

	@GetMapping("/doctor")
	public ResponseEntity<ApiResponseDto> allDoctorAppointments(){
		return appointmentService.getByDoctor();
	}


	@GetMapping("/patient")
	public ResponseEntity<ApiResponseDto> allPatientAppointments(){
		return appointmentService.getByPatient();
	}
	

	@PostMapping
	public ResponseEntity<ApiResponseDto> saveAppointments(@RequestBody AppointmentRequestDto newAppointment){
		return appointmentService.create(newAppointment);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDto> updateAppointmentStatus(@PathVariable Long id, @RequestBody AppointmentStatus status){
		return appointmentService.updateStatus(id, status);
	}
	
}
