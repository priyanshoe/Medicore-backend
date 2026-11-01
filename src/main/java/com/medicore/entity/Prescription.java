package com.medicore.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicore.dto.PrescriptionRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Prescription {

	public Prescription(PrescriptionRequestDto prescription) {

		this.instructions = prescription.getInstructions();
		this.notes = prescription.getNotes();
		this.followUpDate = prescription.getFollowUpDate();
		this.medicines = prescription.getMedicines();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", nullable = false)
	@JsonIgnore
	private User doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	@JsonIgnore
	private User patient;
	
	@OneToOne
	@JoinColumn(name = "appointment_id", nullable = false)
	@JsonIgnore
	private Appointment appointment;
	
	@OneToMany(
        mappedBy = "prescription",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
	private List<Medicine> medicines = new ArrayList<Medicine>();
	
	private String instructions;
	private String notes;
	private Date followUpDate;
	
	
	
}
