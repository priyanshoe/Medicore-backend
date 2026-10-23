package com.medicore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDto {

	private UserResponseDto user;
	private DoctorProfileResponseDto doctorProfile;
	private String token;
}
