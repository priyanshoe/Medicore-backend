package com.medicore.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.medicore.entity.Medicine;

import lombok.Data;

@Data
public class PrescriptionRequestDto {

	private Long patientId;
	private Long appointmentId;
	private List<Medicine> medicines = new ArrayList<Medicine>();
	private String instructions;
	private String notes;
	private Date followUpDate;
}
