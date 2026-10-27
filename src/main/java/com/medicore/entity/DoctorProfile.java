package com.medicore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicore.dto.DoctorProfileRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DoctorProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	@JsonIgnore
	private User user;
	
	private String name;
	private String phone;
	private String specialization;
	private String qualification;
	private int experience;
	private Double consultationFee;
	private String profileImage;
	private String availability;
	
	public DoctorProfile(DoctorProfileRequestDto profile) {
		this.phone = profile.getPhone();
		this.specialization = profile.getSpecialization();
		this.qualification = profile.getQualification();
		this.experience = profile.getExperience();
		this.consultationFee = profile.getConsultationFee();
		this.profileImage = profile.getPhone();
		this.availability = profile.getAvailability();
	}
}
