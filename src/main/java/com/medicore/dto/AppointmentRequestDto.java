package com.medicore.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentRequestDto {

	private Long doctorId;
	private LocalDate date;
    private LocalTime time;
    private String reason;
}
