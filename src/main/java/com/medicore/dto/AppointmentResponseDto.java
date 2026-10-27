package com.medicore.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medicore.ENUM.AppointmentStatus;
import com.medicore.entity.Appointment;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponseDto {
	private Long id;
	private HashMap<String, String> patient = new HashMap<String, String>();
	private HashMap<String, String> doctor = new HashMap<String, String>();
	private LocalDate date;
	private LocalTime time;
	private String reason;
	private AppointmentStatus status;
	
	public AppointmentResponseDto(Appointment appointment, DoctorProfileResponseDto doctor) {
		this.id = appointment.getId();
		this.doctor.put("name", doctor.getName());
		this.doctor.put("specialization", doctor.getSpecialization());
		this.date = appointment.getDate();
		this.time = appointment.getTime();
		this.reason = appointment.getReason();
		this.status = appointment.getStatus();
	}
}
