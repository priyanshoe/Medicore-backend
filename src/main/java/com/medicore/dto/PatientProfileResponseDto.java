package com.medicore.dto;

import java.sql.Date;

import com.medicore.ENUM.Gender;
import com.medicore.entity.PatientProfile;

import lombok.Data;

@Data
public class PatientProfileResponseDto {
	private Long id;
	private Long userId;
	private String name;
	private String phone;
	private Date dateOfBirth;
	private Gender gender;
	private String address;
	
	public PatientProfileResponseDto(PatientProfile profile) {
		this.id = profile.getId();
		this.userId = profile.getUser().getId();
		this.name = profile.getName();
		this.phone = profile.getPhone();
		this.dateOfBirth = profile.getDateOfBirth();
		this.gender = profile.getGender();
		this.address = profile.getAddress();
	}

}
