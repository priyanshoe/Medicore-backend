package com.medicore.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medicore.dto.ApiResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepo;
	
	// only for admin
	public ResponseEntity<ApiResponseDto> getAll(){
		// check user isAdmin
		List<Appointment> appointments = appointmentRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointments fetched", appointments));
	}
}
