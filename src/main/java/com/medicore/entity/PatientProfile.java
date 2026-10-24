package com.medicore.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicore.ENUM.Gender;
import com.medicore.dto.PatientProfileRequestDto;

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
public class PatientProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	@JsonIgnore
	private User user;
	
	private String name;
	private String phone;
	private Date dateOfBirth;
	private Gender gender;
	private String address;
	
	public PatientProfile(PatientProfileRequestDto profile) {
		this.name = profile.getName();
		this.phone = profile.getPhone();
		this.dateOfBirth = profile.getDateOfBirth();
		this.gender = profile.getGender();
		this.address = profile.getAddress();
	}

}
