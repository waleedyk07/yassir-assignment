package com.yassir.banking.service.facade;

import com.yassir.banking.dto.AccountDto;
import com.yassir.banking.dto.CustomerDto;
import com.yassir.banking.dto.OpenAccountDto;
import com.yassir.banking.exception.CustomerNotFoundException;

public interface ICustomerFacade {
	public CustomerDto create(final CustomerDto customerDto);

	public AccountDto createAccount(final Integer customerId, final OpenAccountDto openAccountDto)
			throws CustomerNotFoundException;
}
