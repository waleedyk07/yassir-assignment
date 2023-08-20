package com.yassir.banking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class OpenAccountDto {
	
	@NotBlank(message = "accountTitle cannot be empty")
	private String accountTitle;

	@Positive(message = "initialAmount should be greater than zero")
	private double initialAmount;

	public OpenAccountDto(final String accountTitle, final double initialAmount) {
		this.accountTitle = accountTitle;
		this.initialAmount = initialAmount;
	}

	@Override
	public String toString() {
		return "OpenAccountDto [accountTitle=" + accountTitle + ", initialAmount=" + initialAmount + "]";
	}

}
