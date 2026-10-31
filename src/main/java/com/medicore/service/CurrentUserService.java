package com.medicore.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.medicore.entity.User;
import com.medicore.exception.UnAuthorizeException;
import com.medicore.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CurrentUserService {

	private final UserRepository userRepo;

	// only for backEnd
	public User getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName(); 
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new UnAuthorizeException("user not logged in"));
	}
}
