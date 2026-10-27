package com.medicore.dto;

import lombok.Data;

@Data
public class DoctorProfileRequestDto {

	private String phone;
	private String specialization;
	private String qualification;
	private int experience;
	private Double consultationFee;
	private String profileImage;
	private String availability;
}
