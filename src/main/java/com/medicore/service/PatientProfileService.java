package com.medicore.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.medicore.ENUM.UserRole;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.PatientProfileRequestDto;
import com.medicore.dto.PatientProfileResponseDto;
import com.medicore.entity.Appointment;
import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.ConflictException;
import com.medicore.exception.NotFoundException;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.AppointmentRepository;
import com.medicore.repository.PatientProfileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PatientProfileService {

	private final PatientProfileRepository profileRepo;
	private final AppointmentRepository appointmentRepo;
	private final CurrentUserService currentUserService;

	

	

	public ResponseEntity<ApiResponseDto> getAllProfiles() {
		List<PatientProfileResponseDto> profiles = profileRepo.findAll()
				.stream().map(PatientProfileResponseDto::new).toList();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "patient profiles fetched", profiles));
	}
	

	public ResponseEntity<ApiResponseDto> getUserProfileById(Long id) {
		PatientProfile profile = profileRepo.findById(id).orElseThrow(()-> new NotFoundException("profile with id: "+id));
		PatientProfileResponseDto response = new PatientProfileResponseDto(profile);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "patient profile fetched", response));
	}
	
	
	public ResponseEntity<ApiResponseDto> getProfilesByDoctor() {
		User doctor = currentUserService.getLoginUser();
		List<Appointment> appointments = appointmentRepo.findAllByDoctor(doctor);
		Set<Long> patientIds = appointments.stream().map(appointment -> appointment.getPatient().getId()).collect(Collectors.toSet());
		Map<Long, PatientProfile> profiles = profileRepo.findAllByUser_IdIn(patientIds)
				.stream().collect(Collectors.toMap(
						profile -> profile.getUser().getId(),
						profile -> profile));
		
		if(!doctor.getRole().equals(UserRole.DOCTOR)) {
			throw new UnAuthorizeException();
		}
		List<PatientProfileResponseDto> response = patientIds.stream()
				.map(id -> {
					PatientProfile profile = profiles.get(id);
					return new PatientProfileResponseDto(profile);
				})
				.toList();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "patient profile fetched", response));
	}
	
	

	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<ApiResponseDto> updateProfile(Long id, PatientProfileRequestDto reqProfile) {
		PatientProfile existingProfile = profileRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("profile with id: "+id));
		User authUser = currentUserService.getLoginUser();
		if(!authUser.getId().equals(existingProfile.getUser().getId())) {
			throw new UnAuthorizeException("profile don't belong's to you");
		}
		existingProfile.setName(reqProfile.getName());
		existingProfile.getUser().setName(reqProfile.getName());
		existingProfile.setPhone(reqProfile.getPhone());
		existingProfile.setDateOfBirth(reqProfile.getDateOfBirth());
		existingProfile.setGender(reqProfile.getGender());
		existingProfile.setAddress(reqProfile.getAddress());
		profileRepo.save(existingProfile);
		
		PatientProfileResponseDto response = new PatientProfileResponseDto(existingProfile);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, "patient profile updated", response));
	}
	
	
	
	
	
	// only for backEnd
	public PatientProfileResponseDto registerProfile(User user, PatientProfileRequestDto profile){
		if(profile.equals(null)) {
			throw new BadRequestException("no profile data");
		}
		if(profileRepo.existsByUser(user)) {
			throw new ConflictException("patient profile already exist");
		}
		PatientProfile newProfile = new PatientProfile(profile);
		newProfile.setName(user.getName());
		newProfile.setUser(user);
		PatientProfile savedProfile = profileRepo.save(newProfile);
		PatientProfileResponseDto response = new PatientProfileResponseDto(savedProfile);
		
		return response;
	}
	
	// only for backEnd
	public PatientProfileResponseDto getUserProfile(User user) {
		Optional<PatientProfile> profile = profileRepo.findByUser(user);
		if(profile.isEmpty()) {
			return null;
		}
		return new PatientProfileResponseDto(profile.get());
	}
	
}
