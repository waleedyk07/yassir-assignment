package com.yassir.banking.service;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.BalanceEntity;
import com.yassir.banking.exception.InsufficientBalanceException;

public interface IBalanceService {
	public BalanceEntity createBalanceForAccount(final double amount, final AccountEntity account);

	public BalanceEntity getBalanceForAccount(final AccountEntity account);

	public BalanceEntity depositBalanceForAccount(final double amount, final AccountEntity account);

	public BalanceEntity deductBalanceForAccount(final double amount, final AccountEntity account) throws InsufficientBalanceException;
}
