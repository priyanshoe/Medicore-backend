package com.medicore.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medicore.exception.BadCredentialsException;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetails implements UserDetailsService {

	private final UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.medicore.entity.User user =  userRepo.findByEmail(username).orElseThrow(()-> new BadCredentialsException());
		return User.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().toString())
				.build();
	}

}
