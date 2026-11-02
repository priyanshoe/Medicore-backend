package com.medicore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.PrescriptionRequestDto;
import com.medicore.service.PrescriptionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

	private final PrescriptionService prescriptionService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getAll(){
		return prescriptionService.getAllPrescription();
	}

	@GetMapping("/doctor")
	public ResponseEntity<ApiResponseDto> getByDoctor(){
		return prescriptionService.getPrescriptionByDoctor();
	}
	
	@GetMapping("/patient")
	public ResponseEntity<ApiResponseDto> getByPatient(){
		return prescriptionService.getPrescriptionByPatient();
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto> addPrescription(@RequestBody PrescriptionRequestDto newPrescription){
		return prescriptionService.addPrescription(newPrescription);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionRequestDto newPrescription){
		return prescriptionService.updatePrescription(id,newPrescription);
	}
}
