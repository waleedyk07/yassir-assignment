package com.yassir.banking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassir.banking.dto.AccountDto;
import com.yassir.banking.dto.CustomerDto;
import com.yassir.banking.dto.OpenAccountDto;
import com.yassir.banking.dto.mapper.AccountMapper;
import com.yassir.banking.dto.mapper.CustomerMapper;
import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.enums.TransactionType;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.service.IAccountService;
import com.yassir.banking.service.IBalanceService;
import com.yassir.banking.service.ICustomerService;
import com.yassir.banking.service.ITransactionService;

@Service
public class CustomerFacadeImpl implements ICustomerFacade {

	@Autowired
	public CustomerFacadeImpl(final ICustomerService customerService, final IAccountService accountService,
			final IBalanceService balanceService, final ITransactionService transactionService) {
		this.customerService = customerService;
		this.accountService = accountService;
		this.balanceService = balanceService;
		this.transactionService = transactionService;

	}

	private final ICustomerService customerService;
	private final IAccountService accountService;
	private final IBalanceService balanceService;
	private final ITransactionService transactionService;

	@Override
	public CustomerDto create(final CustomerDto customerDto) {
		return CustomerMapper.toCustomerDto(customerService.createCustomer(customerDto.getCustomerFullName()));
	}

	@Override
	public AccountDto createAccount(final Integer customerId, final OpenAccountDto openAccountDto)
			throws CustomerNotFoundException {

		CustomerEntity customer = customerService.getCustomerById(customerId);
		AccountEntity account = accountService.createForCustomer(openAccountDto.getAccountTitle(), customer);
		balanceService.createBalanceForAccount(openAccountDto.getInitialAmount(), account);
		transactionService.createTransactionLog(null, account, TransactionType.INITIAL_DEPOSIT,
				openAccountDto.getInitialAmount());

		return AccountMapper.toAccountDto(account);
	}

}
