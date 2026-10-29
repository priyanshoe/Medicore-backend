package com.medicore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.DoctorProfileRequestDto;
import com.medicore.dto.DoctorProfileResponseDto;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.ConflictException;
import com.medicore.exception.NotFoundException;
import com.medicore.repository.DoctorProfileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorProfileService {
	
	private final DoctorProfileRepository profileRepo;
	
	public ResponseEntity<ApiResponseDto> getAllprofiles(){
		List<DoctorProfile> doctorProfiles = profileRepo.findAll();
		
		// convert profile -> DoctorProfileResponseDto
		List<DoctorProfileResponseDto> responseDtos = new ArrayList<DoctorProfileResponseDto>();
		for(DoctorProfile profile:doctorProfiles) {
			DoctorProfileResponseDto responseDto = new DoctorProfileResponseDto(profile);
			responseDtos.add(responseDto);
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto(true,"doctor profiles fetched", responseDtos));
	}
		
	// only for backEnd
	public DoctorProfileResponseDto registerProfile(User user, DoctorProfileRequestDto profile){
		if(profile.equals(null)) {
			throw new BadRequestException("no profile data");
		}
		if(profileRepo.existsByUser(user)) {
			throw new ConflictException("doctor profile already exist");
		}
		DoctorProfile newProfile =  new DoctorProfile(profile);
		newProfile.setName(user.getName());
		newProfile.setUser(user);
		DoctorProfile savedProfile = profileRepo.save(newProfile);
		DoctorProfileResponseDto response = new DoctorProfileResponseDto(savedProfile);

		return response;
	}
	

	// only for backEnd
	public DoctorProfileResponseDto getUserProfile(User user) {
		Optional<DoctorProfile> profile = profileRepo.findByUser(user);
		if(profile.isEmpty()) {
			throw new NotFoundException("doctor profile not found");
		}
		return new DoctorProfileResponseDto(profile.get());
	}

}
