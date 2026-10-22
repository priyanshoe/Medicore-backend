package com.medicore.dto;

import com.medicore.ENUM.UserRole;

import lombok.Data;

@Data
public class UserRequestDto {

	private String name;
	private String email;
	private String password;
	private UserRole role;
}
