package com.yassir.banking.dto;

import lombok.Getter;

@Getter
public class AccountDto {

	private Integer accountNumber;
	private String accountTitle;

	public AccountDto(final Integer accountNumber, final String accountTitle) {
		this.accountNumber = accountNumber;
		this.accountTitle = accountTitle;
	}

	@Override
	public String toString() {
		return "AccountDto [accountNumber=" + accountNumber + ", accountTitle=" + accountTitle + "]";
	}

}
