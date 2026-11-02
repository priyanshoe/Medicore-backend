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

import com.medicore.ENUM.UserRole;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.PrescriptionRequestDto;
import com.medicore.dto.PrescriptionResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.Medicine;
import com.medicore.entity.Prescription;
import com.medicore.entity.User;
import com.medicore.exception.ConflictException;
import com.medicore.exception.NotFoundException;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.AppointmentRepository;
import com.medicore.repository.DoctorProfileRepository;
import com.medicore.repository.PrescriptionRepository;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrescriptionService {

	private final PrescriptionRepository prescriptionRepo;
	private final CurrentUserService currentUserService;
	private final UserRepository userRepo;
	private final AppointmentRepository appointmentRepo;
	private final DoctorProfileRepository doctorProfileRepo;;
	
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> getAllPrescription(){
		List<Prescription> prescriptions = prescriptionRepo.findAll();
		// convert to Dto
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "prescriptions fetched", prescriptions));
	}
	
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> getPrescriptionByDoctor(){
		User doctor = currentUserService.getLoginUser();
		if(!doctor.getRole().equals(UserRole.DOCTOR)) {
			throw new UnAuthorizeException();
		}
		
		List<PrescriptionResponseDto> prescriptions = prescriptionRepo.findAllByDoctor(doctor)
				.stream().map(PrescriptionResponseDto::new).toList();
		// convert to Dto
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "doctor's prescriptions fetched", prescriptions));
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<ApiResponseDto> getPrescriptionByPatient(){
		User patient = currentUserService.getLoginUser();
		if(!patient.getRole().equals(UserRole.PATIENT)) {
			throw new UnAuthorizeException();
		}
		List<Prescription> prescriptions = prescriptionRepo.findAllByPatient(patient);
		
		Set<Long> doctorIds = prescriptions.stream()
				.map(pres -> pres.getDoctor().getId())
				.collect(Collectors.toSet());
		Map<Long, DoctorProfile> doctorprofiles = doctorProfileRepo.findAllByUser_IdIn(doctorIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(), 
						profile -> profile));
		List<PrescriptionResponseDto> response = prescriptions.stream()
				.map(pres -> {
					DoctorProfile doctorProfile = doctorprofiles.get(pres.getDoctor().getId());
					return new PrescriptionResponseDto(pres, doctorProfile);
				})
				.toList();		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "patient's prescriptions fetched", response));
	}

	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> addPrescription(PrescriptionRequestDto newPrescription){
		User doctor = currentUserService.getLoginUser();
		User patient = userRepo.findById(newPrescription.getPatientId())
				.orElseThrow(()-> new NotFoundException("patient with id: "+newPrescription.getPatientId()));
		Appointment appointment = appointmentRepo.findById(newPrescription.getAppointmentId())
				.orElseThrow(()-> new NotFoundException("appointment with id: "+newPrescription.getAppointmentId()));
		if(prescriptionRepo.existsByAppointment(appointment)) {
			throw new ConflictException("prescription for this appointment");			
		}
		if(!doctor.getRole().equals(UserRole.DOCTOR)) {
			throw new UnAuthorizeException();
		}
		if(!appointment.getDoctor().equals(doctor)) {
			throw new UnAuthorizeException("appointment dosn't belogs to you");
		}
		if(!appointment.getPatient().equals(patient)) {
			throw new UnAuthorizeException("appointment dosn't belogs to this patient");
		}
		
		Prescription prescription = new Prescription(newPrescription);
		prescription.setDoctor(doctor);
		prescription.setPatient(patient);
		prescription.setAppointment(appointment);
		
		List<Medicine> medicines = new ArrayList<Medicine>();
		for(Medicine med:newPrescription.getMedicines()) {
			Medicine newMed = new Medicine(med, prescription);
			medicines.add(newMed);
		}
		prescription.setMedicines(medicines);
		
		Prescription savedPrescription = prescriptionRepo.save(prescription);
		PrescriptionResponseDto response = new PrescriptionResponseDto(savedPrescription);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "prescriptions saved", response));
	}
	
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<ApiResponseDto> updatePrescription(Long id,PrescriptionRequestDto reqPrescription){
		User doctor = currentUserService.getLoginUser();
		Prescription existingPrescription = prescriptionRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("prescription"));
		if(!doctor.getRole().equals(UserRole.DOCTOR)) {
			throw new UnAuthorizeException();
		}
		if(!existingPrescription.getDoctor().equals(doctor)) {
			throw new UnAuthorizeException("appointment dosn't belogs to you");
		}
		
		existingPrescription.setInstructions(reqPrescription.getInstructions());
		existingPrescription.setDiagnosis(reqPrescription.getDiagnosis());
		existingPrescription.setNotes(reqPrescription.getNotes());
		existingPrescription.setFollowUpDate(reqPrescription.getFollowUpDate());
		
		if(!existingPrescription.getMedicines().equals(reqPrescription.getMedicines())) {
			for(Medicine med:reqPrescription.getMedicines()) {
				Medicine newMed = new Medicine(med, existingPrescription);
				existingPrescription.getMedicines().add(newMed);
			}
		}
		
		Prescription savedPrescription = prescriptionRepo.save(existingPrescription);
		PrescriptionResponseDto response = new PrescriptionResponseDto(savedPrescription);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "prescriptions updated", response));
	}
	
	
	
}
