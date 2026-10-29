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
import com.medicore.dto.PatientProfileResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.NotFoundException;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.AppointmentRepository;
import com.medicore.repository.DoctorProfileRepository;
import com.medicore.repository.PatientProfileRepository;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final AuthService authService;
	private final UserRepository userRepo;
	private final DoctorProfileRepository doctorProfileRepo;
	private final PatientProfileRepository patientProfileRepo;
	
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> getAll(){
//		List<AppointmentResponseDto> appointments = appointmentRepo.findAll()
//				.stream().map(appointment ->
//				new AppointmentResponseDto(appointment, 
//						doctorProfileRepo.findByUser(appointment.getDoctor())
//						.orElseThrow(()-> new NotFoundException("doctor")), 
//						patientProfileRepo.findByUser(appointment.getPatient())
//						.orElseThrow(()-> new NotFoundException("doctor"))
//						)).toList();
		List<Appointment> appointments = appointmentRepo.findAll();
		
		// fetch doctor profiles
		Set<Long> doctorIds = appointments.stream()
				.map(appointment -> appointment.getDoctor().getId())
				.collect(Collectors.toSet());
		Map<Long, DoctorProfile> doctorProfiles = doctorProfileRepo.findAllByUser_IdIn(doctorIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(), 
						profile -> profile));
		
		// fetch patient profiles
		Set<Long> patientIds = appointments.stream()
				.map(appointment -> appointment.getPatient().getId())
				.collect(Collectors.toSet());
		Map<Long, PatientProfile> patientProfiles = patientProfileRepo.findAllByUser_IdIn(patientIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(), 
						profile -> profile));
		List<AppointmentResponseDto> response = appointments.stream()
				.map(appointment -> {
				DoctorProfile docotorProfile = doctorProfiles.get(appointment.getDoctor().getId());
				PatientProfile patientProfile = patientProfiles.get(appointment.getPatient().getId());
				return new AppointmentResponseDto(appointment, docotorProfile, patientProfile);
				})
				.toList();
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointments fetched", response));
	}
	

	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> getByDoctor(){
		User authUser = authService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByDoctor(authUser);
		
		Set<Long> patientIds = appointments.stream()
				.map(appointment -> appointment.getPatient().getId())
				.collect(Collectors.toSet());
		Map<Long, PatientProfile> patientProfiles = patientProfileRepo.findAllByUser_IdIn(patientIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(), 
						profile -> profile));
		List<AppointmentResponseDto> responseDto = appointments.stream()
				.map(appointment -> {
					PatientProfile profile = patientProfiles.get(appointment.getPatient().getId());
					return new AppointmentResponseDto(appointment, profile);
				}).toList();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"doctor's appointments fetched", responseDto));
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
					return new AppointmentResponseDto(appointment, profile);
				}).toList();
		
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
	
	
	
	public ResponseEntity<ApiResponseDto> updateStatus(Long id, AppointmentStatus status){
		Appointment existingAppointment = appointmentRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("appointment with id: "+id));

		User loggedinUser = authService.getLoginUser();
		if(loggedinUser.getRole().equals(UserRole.DOCTOR)
				&& !existingAppointment.getDoctor().equals(loggedinUser)) {
			throw new UnAuthorizeException("appointment don'nt belongs to you");
		}
		
		if(loggedinUser.getRole().equals(UserRole.PATIENT)) {
			if(!existingAppointment.getPatient().equals(loggedinUser)) {
				throw new UnAuthorizeException("appointment don'nt belongs to you");			
			}
			if( status.equals(AppointmentStatus.COMPLETED)
				|| status.equals(AppointmentStatus.ACCEPTED)) {
				throw new UnAuthorizeException("you are not authoraize to performe this action");
			}
		}
		
		
		existingAppointment.setStatus(status);
		Appointment updatedAppointment = appointmentRepo.save(existingAppointment);
		DoctorProfile doctorProfile = doctorProfileRepo.findByUser(existingAppointment.getDoctor())
				.orElseThrow(()-> new NotFoundException("doctor profile not found"));
		PatientProfile patientProfile = patientProfileRepo.findByUser(existingAppointment.getPatient())
				.orElseThrow(()-> new NotFoundException("doctor profile not found"));
		AppointmentResponseDto response = new AppointmentResponseDto(updatedAppointment,doctorProfile, patientProfile);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"appointment status updated", response));
	}
}
