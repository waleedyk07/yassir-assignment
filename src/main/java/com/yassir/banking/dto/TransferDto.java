package com.yassir.banking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class TransferDto {

	@NotNull(message = "toAccountNumber cannot be empty")
	private Integer toAccountNumber;
	
	@Positive(message = "amount should be greater than zero")
	private double amount;

	public TransferDto(Integer toAccountNumber, double amount) {
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TransferDto [toAccountNumber=" + toAccountNumber + ", amount=" + amount + "]";
	}
}
