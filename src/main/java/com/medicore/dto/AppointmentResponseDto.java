package com.medicore.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medicore.ENUM.AppointmentStatus;
import com.medicore.entity.Appointment;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.PatientProfile;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponseDto {
	private Long id;
	private HashMap<String, String> patient;
	private HashMap<String, String> doctor;
	private LocalDate date;
	private LocalTime time;
	private String reason;
	private AppointmentStatus status;
	
	public AppointmentResponseDto(Appointment appointment, DoctorProfile doctor) {
		this.id = appointment.getId();
		this.doctor = new HashMap<String, String>();
		this.doctor.put("name", doctor.getName());
		this.doctor.put("specialization", doctor.getSpecialization());
		this.date = appointment.getDate();
		this.time = appointment.getTime();
		this.reason = appointment.getReason();
		this.status = appointment.getStatus();
	}
	

	public AppointmentResponseDto(Appointment appointment, PatientProfile patient) {
		this.id = appointment.getId();
		this.patient = new HashMap<String, String>();
		this.patient.put("id", patient.getId().toString());
		this.patient.put("name", patient.getName());
		this.patient.put("phone", patient.getPhone());
		this.patient.put("email", patient.getUser().getEmail());
		this.date = appointment.getDate();
		this.time = appointment.getTime();
		this.reason = appointment.getReason();
		this.status = appointment.getStatus();
	}
}
