package com.medicore.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.Medicine;
import com.medicore.entity.Prescription;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionResponseDto {

	private Long id;
	private HashMap<String, String> doctor = new HashMap<String, String>();
	private HashMap<String, String> patient = new HashMap<String, String>();
	private Long appointmentId;
	private List<Medicine> medicines = new ArrayList<Medicine>();
	private String diagnosis;
	private String instructions;
	private String notes;
	private Date followUpDate;
	
	public PrescriptionResponseDto(Prescription prescription) {
		this.id = prescription.getId();
		
		this.doctor.put("id", prescription.getDoctor().getId().toString());
		this.doctor.put("name", prescription.getDoctor().getName());

		this.patient.put("id", prescription.getPatient().getId().toString());
		this.patient.put("name", prescription.getPatient().getName());
		
		if(prescription.getAppointment() != null) {
			this.appointmentId = prescription.getAppointment().getId();
		}
		this.medicines = prescription.getMedicines();
		this.diagnosis = prescription.getDiagnosis();
		this.instructions = prescription.getInstructions();
		this.followUpDate = prescription.getFollowUpDate();
	}
	
	public PrescriptionResponseDto(Prescription prescription, DoctorProfile doctor) {
		this.id = prescription.getId();
		
		this.doctor.put("id", prescription.getDoctor().getId().toString());
		this.doctor.put("name", doctor.getName());
		this.doctor.put("profileImage", doctor.getProfileImage());
		this.doctor.put("specialization", doctor.getSpecialization());
		

		this.patient.put("id", prescription.getPatient().getId().toString());
		this.patient.put("name", prescription.getPatient().getName());
		
		if(prescription.getAppointment() != null) {
			this.appointmentId = prescription.getAppointment().getId();
		}
		this.medicines = prescription.getMedicines();
		this.diagnosis = prescription.getDiagnosis();
		this.instructions = prescription.getInstructions();
		this.followUpDate = prescription.getFollowUpDate();
	}
}
