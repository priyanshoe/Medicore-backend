package com.medicore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.ENUM.UserRole;
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
	
	public String register(User newUser) {
		if(newUser.getRole().equals(UserRole.ADMIN)) {
			throw new UnAuthorizeException();
		}
		if(userRepo.existsByEmail(newUser.getEmail())) {
			throw new ConflictException("user");
		}
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userRepo.save(newUser);
		
		return "user registered";
	}
	
	public String login(User reqUser) {		
		try {
			if(!userRepo.existsByEmail(reqUser.getEmail())) {
				throw new BadCredentialsException();
			}
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));			
		}catch (Exception e) {
			throw new BadCredentialsException();
		}
				
		return "user loggedIn";
	}

}
