package com.medicore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Medicine {

	public Medicine(Medicine med, Prescription prescription) {
		this.name = med.getName();
		this.dosage = med.getDosage();
		this.frequency = med.getFrequency();
		this.prescription = prescription;
	}
	public Medicine(Medicine med) {
		this.name = med.getName();
		this.dosage = med.getDosage();
		this.frequency = med.getFrequency();
		this.prescription = med.getPrescription();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "presription_id", nullable = false)
	private Prescription prescription;
	
	@Column(nullable = false)
	private String name;
	private String dosage;
	private String frequency;
	
}
