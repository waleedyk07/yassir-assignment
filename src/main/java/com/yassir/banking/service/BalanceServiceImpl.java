package com.yassir.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.BalanceEntity;
import com.yassir.banking.exception.InsufficientBalanceException;
import com.yassir.banking.repository.BalanceRepository;

@Service
public class BalanceServiceImpl implements IBalanceService {

	@Autowired
	public BalanceServiceImpl(final BalanceRepository balanceRepo) {
		this.balanceRepo = balanceRepo;
	}

	private final BalanceRepository balanceRepo;

	@Override
	public BalanceEntity createBalanceForAccount(final double amount, final AccountEntity account) {
		BalanceEntity balance = new BalanceEntity();
		balance.setCurrentBalance(amount);
		balance.setAccount(account);
		return balanceRepo.save(balance);
	}

	@Override
	public BalanceEntity getBalanceForAccount(final AccountEntity account) {
		return balanceRepo.findByAccountId(account.getId());
	}

	@Override
	public BalanceEntity depositBalanceForAccount(final double amount, final AccountEntity account) {
		BalanceEntity balance = getBalanceForAccount(account);
		balance.setCurrentBalance(balance.getCurrentBalance() + amount);
		return balanceRepo.save(balance);
	}

	@Override
	public BalanceEntity deductBalanceForAccount(final double amount, final AccountEntity account) throws InsufficientBalanceException {
		BalanceEntity balance = getBalanceForAccount(account);
		if(balance.getCurrentBalance() >= amount) {
			balance.setCurrentBalance(balance.getCurrentBalance() - amount);
			return balanceRepo.save(balance);
		}
		throw new InsufficientBalanceException("Insufficient balance.");
	}

}
