package com.yassir.banking.dto;

import java.util.Date;

import com.yassir.banking.enums.TransactionType;

import lombok.Getter;

@Getter
public class TransactionDto {

	private Integer transactionId;
	private Integer fromAccountNumber;
	private Integer toAccountNumber;
	private TransactionType transactionType;
	private Date transactionDate;
	private double ammount;

	public TransactionDto(final Integer transactionId, final Integer toAccountNumber,
			final TransactionType transactionType, final Date transactionDate, final double ammount) {
		this.transactionId = transactionId;
		this.toAccountNumber = toAccountNumber;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.ammount = ammount;
	}
	
	public void setFromAccountNumber(Integer fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

}
