package com.yassir.banking.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class BalanceDto {
	
	@Positive(message = "accountBalance should be greater than zero")
	private double accountBalance;
	
	public BalanceDto() {
	}

	public BalanceDto(final double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "BalanceDto [accountBalance=" + accountBalance + "]";
	}


}
