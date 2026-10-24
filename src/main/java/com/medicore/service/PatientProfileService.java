package com.medicore.service;

import org.springframework.stereotype.Service;

import com.medicore.dto.DoctorProfileRequestDto;
import com.medicore.dto.DoctorProfileResponseDto;
import com.medicore.dto.PatientProfileRequestDto;
import com.medicore.dto.PatientProfileResponseDto;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.ConflictException;
import com.medicore.repository.PatientProfileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PatientProfileService {

	private final PatientProfileRepository profileRepo;

	// only for backEnd
	public PatientProfileResponseDto registerProfile(User user, PatientProfileRequestDto profile){
		if(profile.equals(null)) {
			throw new BadRequestException("no profile data");
		}
		if(profileRepo.existsByUser(user)) {
			throw new ConflictException("patient profile already exist");
		}
		PatientProfile newProfile = new PatientProfile(profile);
		newProfile.setUser(user);
		PatientProfile savedProfile = profileRepo.save(newProfile);
		PatientProfileResponseDto response = new PatientProfileResponseDto(savedProfile);
		
		return response;
	}
	
}
