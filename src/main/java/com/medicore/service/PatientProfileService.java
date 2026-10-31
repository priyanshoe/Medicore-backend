package com.medicore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.PatientProfileRequestDto;
import com.medicore.dto.PatientProfileResponseDto;
import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.ConflictException;
import com.medicore.exception.NotFoundException;
import com.medicore.repository.PatientProfileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PatientProfileService {

	private final PatientProfileRepository profileRepo;

	

	

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
