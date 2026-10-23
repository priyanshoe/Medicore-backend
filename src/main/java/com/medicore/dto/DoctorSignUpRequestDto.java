package com.medicore.dto;

import lombok.Data;

@Data
public class DoctorSignUpRequestDto {

	private UserRequestDto user;
	private DoctorProfileRequestDto pofile;
}
