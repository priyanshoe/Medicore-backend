package com.medicore.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponseDto> handleAccessDenied(
	        AccessDeniedException ex) {

	    return ResponseEntity.status(HttpStatus.FORBIDDEN)
	            .body(new ApiResponseDto(
	                    false,
	                    "You are not authorized to access this resource",
	                    null
	            ));
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
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponseDto> handleNotFoundException(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponseDto(false, ex.getMessage(), null));
	}
	

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponseDto> handleDataIntegrityViolationExceptionException(DataIntegrityViolationException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiResponseDto(false, "appointment already exists", null));
	}
	
}


