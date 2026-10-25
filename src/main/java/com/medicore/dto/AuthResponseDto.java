package com.medicore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDto {

	private UserResponseDto user;
	private DoctorProfileResponseDto doctorProfile;
	private PatientProfileResponseDto patientProfile;
	private String token;
	
	public AuthResponseDto(
			UserResponseDto user,
			DoctorProfileResponseDto profile,
			String token) {
		this.user = user;
		this.doctorProfile = profile;
		this.patientProfile = null;
		this.token = token;
	}
	
	public AuthResponseDto(
			UserResponseDto user,
			PatientProfileResponseDto profile,
			String token) {
		this.user = user;
		this.doctorProfile = null;
		this.patientProfile = profile;
		this.token = token;
	}
}
