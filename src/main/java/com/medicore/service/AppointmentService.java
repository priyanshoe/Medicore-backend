package com.medicore.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.medicore.dto.ApiResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.entity.User;
import com.medicore.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final AuthService authService;
	
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> getAll(){
		List<Appointment> appointments = appointmentRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointments fetched", appointments));
	}
	

	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> getByDoctor(){
		User authUser = authService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByDoctor(authUser);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"doctor's appointments fetched", appointments));
	}


	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<ApiResponseDto> getByPatient(){
		User authUser = authService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByPatient(authUser);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"patient's appointments fetched", appointments));
	}
}
