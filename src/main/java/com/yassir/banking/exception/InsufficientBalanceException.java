package com.yassir.banking.exception;

public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String errorMessage) {
		super(errorMessage);
	}

}
