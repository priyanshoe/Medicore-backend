package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
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
}
