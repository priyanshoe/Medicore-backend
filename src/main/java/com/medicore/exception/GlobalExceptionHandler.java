package com.medicore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medicore.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto> handleInternalException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto(false, "Internal Server Error: "+ ex.getMessage(), null));
	}
	
	@ExceptionHandler(UnAuthorizeException.class)
	public ResponseEntity<ApiResponseDto> handleUnAuthorizeException(UnAuthorizeException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDto(false,ex.getMessage(),null));
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponseDto> handleBadRequestException(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto(false, ex.getMessage(), null));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto(false, ex.getMessage(), null));
	}
	

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiResponseDto> handleConflictException(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiResponseDto(false, ex.getMessage(), null));
	}
	
}
