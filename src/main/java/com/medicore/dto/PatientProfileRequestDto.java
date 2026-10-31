package com.medicore.dto;

import java.sql.Date;

import com.medicore.ENUM.Gender;

import lombok.Data;

@Data
public class PatientProfileRequestDto {

	private String name;
	private String phone;
	private Date dateOfBirth;
	private Gender gender;
	private String address;

}
