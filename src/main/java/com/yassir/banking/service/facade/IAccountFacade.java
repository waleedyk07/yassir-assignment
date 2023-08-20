package com.yassir.banking.service.facade;

import java.util.List;

import com.yassir.banking.dto.BalanceDto;
import com.yassir.banking.dto.TransactionDto;
import com.yassir.banking.dto.TransferDto;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.exception.InsufficientBalanceException;

public interface IAccountFacade {
	public BalanceDto getBalanceByAccountNumber(final Integer accountNumber) throws AccountNotFoundException;

	public BalanceDto depositBalanceInAccount(final Integer accountNumber, final BalanceDto balanceDto)
			throws AccountNotFoundException;

	public TransactionDto transferAmmount(final Integer fromAccountNumber, final TransferDto transferto)
			throws AccountNotFoundException, InsufficientBalanceException;

	public List<TransactionDto> getTransactionHistory(final Integer accountNumber) throws AccountNotFoundException;

}
