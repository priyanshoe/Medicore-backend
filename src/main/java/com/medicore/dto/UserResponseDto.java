package com.medicore.dto;

import com.medicore.ENUM.UserRole;
import com.medicore.entity.User;

import lombok.Data;

@Data
public class UserResponseDto {

	private Long id;
	private String name;
	private String email;
	private UserRole role;
	
	
	//convert User -> User Response Dto
	public UserResponseDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRole();
	}
}
