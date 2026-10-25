package com.medicore.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicore.ENUM.UserRole;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.AuthResponseDto;
import com.medicore.dto.DoctorProfileRequestDto;
import com.medicore.dto.DoctorProfileResponseDto;
import com.medicore.dto.PatientProfileRequestDto;
import com.medicore.dto.PatientProfileResponseDto;
import com.medicore.dto.UserRequestDto;
import com.medicore.dto.UserResponseDto;
import com.medicore.entity.User;
import com.medicore.exception.BadCredentialsException;
import com.medicore.exception.BadRequestException;
import com.medicore.exception.ConflictException;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.PatientProfileRepository;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepo;
	private final DoctorProfileService doctorProfileService;
	private final PatientProfileService patientProfileService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	
	// registration for doctor
	@Transactional
	public ResponseEntity<ApiResponseDto> register(UserRequestDto newUser, DoctorProfileRequestDto newDoctorProfile) {
		if(newUser.getRole().equals(UserRole.ADMIN)) {
			throw new UnAuthorizeException();
		}
		if(!newUser.getRole().equals(UserRole.DOCTOR)) {
			throw new BadRequestException("invalid role");
		}
		if(userRepo.existsByEmail(newUser.getEmail())) {
			throw new ConflictException("user");
		}
		User user = new User(newUser);
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User savedUser = userRepo.save(user);
		// save profile
		DoctorProfileResponseDto profileRes = doctorProfileService.registerProfile(savedUser, newDoctorProfile);
		// convert to Dto
		UserResponseDto userRes = new UserResponseDto(savedUser);
		AuthResponseDto responseDto = new AuthResponseDto(userRes, profileRes,"dummy token");
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponseDto(true,"doctor registered", responseDto));
	}
	
	
	

	// override registration for patient
	@Transactional
	public ResponseEntity<ApiResponseDto> register(UserRequestDto newUser, PatientProfileRequestDto newPatientProfile) {
		if(newUser.getRole().equals(UserRole.ADMIN)) {
			throw new UnAuthorizeException();
		}
		if(!newUser.getRole().equals(UserRole.PATIENT)) {
			throw new BadRequestException("invalid role");
		}
		if(userRepo.existsByEmail(newUser.getEmail())) {
			throw new ConflictException("user");
		}
		User user = new User(newUser);
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User savedUser = userRepo.save(user);
		// save profile
		PatientProfileResponseDto profileRes = patientProfileService.registerProfile(savedUser, newPatientProfile);
		// convert to Dto
		UserResponseDto userRes = new UserResponseDto(savedUser);
		AuthResponseDto responseDto = new AuthResponseDto(userRes, profileRes,"dummy token");
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponseDto(true,"patient registered", responseDto));
	}
	
	
	
	
	
	public ResponseEntity<ApiResponseDto> login(UserRequestDto reqUser) {		
		try {
			User existingUser = userRepo.findByEmail(reqUser.getEmail())
					.orElseThrow(() -> new BadCredentialsException());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
			UserResponseDto userRes = new UserResponseDto(existingUser);
			// fix profile response
			AuthResponseDto responseDto = new AuthResponseDto(userRes,null,null,"demo token");
			//fetch user profile
			if(existingUser.getRole().equals(UserRole.DOCTOR)) {
				DoctorProfileResponseDto profileDto = doctorProfileService.getUserProfile(existingUser);
				responseDto.setDoctorProfile(profileDto);
			}
			if(existingUser.getRole().equals(UserRole.PATIENT)) {
				PatientProfileResponseDto profileDto = patientProfileService.getUserProfile(existingUser);
				responseDto.setPatientProfile(profileDto);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new ApiResponseDto(true, "user loggedIn" ,responseDto));
		}catch (AuthenticationException e) {
			throw new BadCredentialsException();
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
				
	}

}
