package com.yassir.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yassir.banking.dto.AccountDto;
import com.yassir.banking.dto.CustomerDto;
import com.yassir.banking.dto.OpenAccountDto;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.service.facade.ICustomerFacade;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("customer")
@Validated
public class CustomerController {

	@Autowired
	public CustomerController(final ICustomerFacade customerFacade) {
		this.customerFacade = customerFacade;
	}

	private final ICustomerFacade customerFacade;

	@Operation(summary = "Create a customer with given name", tags = "Create Customer")
	@PostMapping
	public ResponseEntity<CustomerDto> create(@Valid @RequestBody final CustomerDto request) {
		return new ResponseEntity<CustomerDto>(customerFacade.create(request), HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Create an account with given account title for given customer", tags = "Open Acount")
	@PostMapping(path = "{customerId}")
	public ResponseEntity<AccountDto> openAccount(@PathVariable("customerId") final Integer customerId,
			@Valid @RequestBody final OpenAccountDto request) throws CustomerNotFoundException {
		return new ResponseEntity<AccountDto>(customerFacade.createAccount(customerId, request), HttpStatus.ACCEPTED);
	}
}
