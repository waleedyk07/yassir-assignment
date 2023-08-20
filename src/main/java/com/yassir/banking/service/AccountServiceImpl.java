package com.yassir.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.repository.AccountRepository;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	public AccountServiceImpl(final AccountRepository accountRep) {
		this.accountRep = accountRep;
	}

	private final AccountRepository accountRep;

	@Override
	public AccountEntity createForCustomer(final String title, final CustomerEntity customer) {
		AccountEntity account = new AccountEntity();
		account.setTitle(title);
		account.setCustomer(customer);
		return accountRep.save(account);
	}

	@Override
	public AccountEntity getAccountById(final Integer id) throws AccountNotFoundException {
		Optional<AccountEntity> _account = accountRep.findById(id);
		if (_account.isEmpty())
			throw new AccountNotFoundException("account not found");
		else
			return _account.get();
	}

}
