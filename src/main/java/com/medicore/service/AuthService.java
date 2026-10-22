package com.medicore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.entity.User;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public String register(User newUser) {
		
		if(userRepo.existsByEmail(newUser.getEmail())) {
			return "user already exist";
		}
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userRepo.save(newUser);
		
		return "user registered";
	}
	
	public String login(User reqUser) {		
		try {
			if(!userRepo.existsByEmail(reqUser.getEmail())) {
				throw new BadCredentialsException("Bad Credentials");
			}
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));			
		}catch (Exception e) {
			return "Bad Credentials";
		}
				
		return "user loggedIn";
	}

}
