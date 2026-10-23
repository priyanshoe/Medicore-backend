package com.medicore.dto;

import com.medicore.entity.DoctorProfile;

import lombok.Data;

@Data
public class DoctorProfileResponseDto {

	private Long id;
	private Long UserId;
	private String name;
	private String phone;
	private String specialization;
	private String qualification;
	private int experience;
	private Double consultationFee;
	private String profileImage;
	private String availability;
	
	// convert profile -> DoctorProfileResponseDto
	public DoctorProfileResponseDto(DoctorProfile profile){
		this.id = profile.getId();
		this.UserId = profile.getUser().getId();
		this.name = profile.getName();
		this.phone = profile.getPhone();
		this.specialization = profile.getSpecialization();
		this.qualification = profile.getQualification();
		this.experience = profile.getExperience();
		this.consultationFee = profile.getConsultationFee();
		this.profileImage = profile.getProfileImage();
		this.availability = profile.getAvailability();
	}
}