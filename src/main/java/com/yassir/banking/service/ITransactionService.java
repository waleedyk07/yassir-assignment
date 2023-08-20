package com.yassir.banking.service;

import java.util.List;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.TransactionEntity;
import com.yassir.banking.enums.TransactionType;

public interface ITransactionService {
	public TransactionEntity createTransactionLog(final AccountEntity from, final AccountEntity to,
			final TransactionType type, final double amount);
	public List<TransactionEntity> getTransactionsByAccount(final AccountEntity account);
}
