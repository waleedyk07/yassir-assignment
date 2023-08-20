package com.yassir.banking.service.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yassir.banking.dto.BalanceDto;
import com.yassir.banking.dto.TransactionDto;
import com.yassir.banking.dto.TransferDto;
import com.yassir.banking.dto.mapper.BalanceMapper;
import com.yassir.banking.dto.mapper.TransactionMapper;
import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.BalanceEntity;
import com.yassir.banking.enums.TransactionType;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.exception.InsufficientBalanceException;
import com.yassir.banking.service.IAccountService;
import com.yassir.banking.service.IBalanceService;
import com.yassir.banking.service.ITransactionService;

@Service
public class AccountFacadeImpl implements IAccountFacade {

	public AccountFacadeImpl(final IBalanceService balanceService, final IAccountService accountService,
			final ITransactionService transactionService) {
		this.balanceService = balanceService;
		this.accountService = accountService;
		this.transactionService = transactionService;

	}

	private final IBalanceService balanceService;
	private final IAccountService accountService;
	private final ITransactionService transactionService;

	@Override
	public BalanceDto getBalanceByAccountNumber(final Integer accountNumber) throws AccountNotFoundException {
		AccountEntity account = accountService.getAccountById(accountNumber);
		return BalanceMapper.toBalanceDto(balanceService.getBalanceForAccount(account));
	}

	@Override
	public BalanceDto depositBalanceInAccount(final Integer accountNumber, final BalanceDto balanceDto)
			throws AccountNotFoundException {
		AccountEntity account = accountService.getAccountById(accountNumber);
		BalanceEntity balance = balanceService.depositBalanceForAccount(balanceDto.getAccountBalance(), account);
		transactionService.createTransactionLog(null, account, TransactionType.DEPOSIT, balanceDto.getAccountBalance());
		return BalanceMapper.toBalanceDto(balance);
	}

	@Override
	public TransactionDto transferAmmount(final Integer fromAccountNumber, final TransferDto transferDto)
			throws AccountNotFoundException, InsufficientBalanceException {
		AccountEntity fromAccount = accountService.getAccountById(fromAccountNumber);
		AccountEntity toAccount = accountService.getAccountById(transferDto.getToAccountNumber());

		balanceService.deductBalanceForAccount(transferDto.getAmount(), fromAccount);
		balanceService.depositBalanceForAccount(transferDto.getAmount(), toAccount);

		return TransactionMapper.toTransactionDto(transactionService.createTransactionLog(fromAccount, toAccount,
				TransactionType.ACCOUNT_TRANSER, transferDto.getAmount()));
	}

	@Override
	public List<TransactionDto> getTransactionHistory(final Integer accountNumber) throws AccountNotFoundException {
		AccountEntity account = accountService.getAccountById(accountNumber);
		return TransactionMapper.toTransactionDto(transactionService.getTransactionsByAccount(account));
	}

}
