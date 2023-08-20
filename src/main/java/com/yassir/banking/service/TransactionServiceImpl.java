package com.yassir.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.TransactionEntity;
import com.yassir.banking.enums.TransactionType;
import com.yassir.banking.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements ITransactionService {

	public TransactionServiceImpl(final TransactionRepository txRepo) {
		this.txRepo = txRepo;
	}

	private final TransactionRepository txRepo;

	@Override
	public TransactionEntity createTransactionLog(final AccountEntity from, final AccountEntity to, final TransactionType type,
			final double amount) {
		TransactionEntity transaction = new TransactionEntity();
		transaction.setFromAccount(from);
		transaction.setToAccount(to);
		transaction.setType(type);
		transaction.setAmount(amount);
		return txRepo.save(transaction);
	}

	@Override
	public List<TransactionEntity> getTransactionsByAccount(final AccountEntity account) {
		return txRepo.findByFromOrToAccountId(account.getId());
	}

}
