package com.medicore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.medicore.ENUM.AppointmentStatus;
import com.medicore.ENUM.UserRole;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.AppointmentRequestDto;
import com.medicore.dto.AppointmentResponseDto;
import com.medicore.dto.DoctorProfileResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.NotFoundException;
import com.medicore.repository.AppointmentRepository;
import com.medicore.repository.DoctorProfileRepository;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final AuthService authService;
	private final UserRepository userRepo;
	private final DoctorProfileRepository doctorProfileRepo;
	
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> getAll(){
		List<Appointment> appointments = appointmentRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointments fetched", appointments));
	}
	

	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> getByDoctor(){
		User authUser = authService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByDoctor(authUser);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"doctor's appointments fetched", appointments));
	}


	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<ApiResponseDto> getByPatient(){
		User authUser = authService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByPatient(authUser);
		
		Set<Long> doctorIds = appointments.stream()
				.map(appointment -> appointment.getDoctor().getId())
				.collect(Collectors.toSet());
		Map<Long, DoctorProfile> doctorProfiles = doctorProfileRepo.findAllByUser_IdIn(doctorIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(),
						profile -> profile));
		List<AppointmentResponseDto> responseDto = appointments.stream()
				.map(appointment -> {
					DoctorProfile profile = doctorProfiles.get(appointment.getDoctor().getId());
					DoctorProfileResponseDto profileDto = new DoctorProfileResponseDto(profile);
					return new AppointmentResponseDto(appointment, profileDto);
				}).toList();
		
//		List<AppointmentResponseDto> responseDto = new ArrayList<AppointmentResponseDto>();
//		for(Appointment appointment:appointments) {
//			DoctorProfileResponseDto doctorProfile = doctorProfileService.getUserProfile(appointment.getDoctor());
//			AppointmentResponseDto response = new AppointmentResponseDto(appointment, doctorProfile);
//			responseDto.add(response);
//		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"patient's appointments fetched", responseDto));
	}
	

	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<ApiResponseDto> create(AppointmentRequestDto newAppointment){
		User patient = authService.getLoginUser();
		User doctor = userRepo.findById(newAppointment.getDoctorId())
				.orElseThrow(()-> new NotFoundException("doctor"));
		if(!doctor.getRole().equals(UserRole.DOCTOR)) {
			throw new BadRequestException("invalid doctor id");
		}
		
		Appointment appointment = new Appointment();
		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		appointment.setDate(newAppointment.getDate());
		appointment.setTime(newAppointment.getTime());
		appointment.setReason(newAppointment.getReason());
		appointment.setStatus(AppointmentStatus.PENDING);
		
		Appointment savedAppointment = appointmentRepo.save(appointment);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointment created", savedAppointment));
	}
}
