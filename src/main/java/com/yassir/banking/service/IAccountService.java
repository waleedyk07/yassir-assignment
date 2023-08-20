package com.yassir.banking.service;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.exception.AccountNotFoundException;

public interface IAccountService {
	public AccountEntity getAccountById(final Integer id) throws AccountNotFoundException;

	public AccountEntity createForCustomer(final String title, final CustomerEntity customer);

}
