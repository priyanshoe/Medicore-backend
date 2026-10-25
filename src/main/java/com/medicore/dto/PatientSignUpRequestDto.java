package com.medicore.dto;

import lombok.Data;

@Data
public class PatientSignUpRequestDto {
	
	UserRequestDto user;
	PatientProfileRequestDto profile;

}
