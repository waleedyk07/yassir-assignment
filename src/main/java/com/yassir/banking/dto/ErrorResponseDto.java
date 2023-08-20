package com.yassir.banking.dto;

public class ErrorResponseDto {

	private String errorMessage;

	public ErrorResponseDto() {
	}

	public ErrorResponseDto(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return "ERROR! - " + errorMessage;
	}
}
