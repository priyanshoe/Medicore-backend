package com.medicore.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.ENUM.UserRole;
import com.medicore.dto.ApiResponseDto;
import com.medicore.dto.AuthResponseDto;
import com.medicore.dto.UserRequestDto;
import com.medicore.dto.UserResponseDto;
import com.medicore.entity.User;
import com.medicore.exception.BadCredentialsException;
import com.medicore.exception.ConflictException;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	
	public ResponseEntity<ApiResponseDto> register(UserRequestDto newUser) {
		if(newUser.getRole().equals(UserRole.ADMIN)) {
			throw new UnAuthorizeException();
		}
		if(userRepo.existsByEmail(newUser.getEmail())) {
			throw new ConflictException("user");
		}
		User user = new User(newUser);
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User savedUser = userRepo.save(user);
		UserResponseDto userRes = new UserResponseDto(savedUser);
		AuthResponseDto responseDto = new AuthResponseDto(userRes,"dummy token");
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponseDto(true,"user registered", responseDto));
	}
	
	
	public ResponseEntity<ApiResponseDto> login(UserRequestDto reqUser) {		
		try {
			User existingUser = userRepo.findByEmail(reqUser.getEmail())
					.orElseThrow(() -> new BadCredentialsException());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
			UserResponseDto userRes = new UserResponseDto(existingUser);
			AuthResponseDto responseDto = new AuthResponseDto(userRes,"demo token");
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new ApiResponseDto(true, "user loggedIn" ,responseDto));
		}catch (AuthenticationException e) {
			throw new BadCredentialsException();
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
				
	}

}
