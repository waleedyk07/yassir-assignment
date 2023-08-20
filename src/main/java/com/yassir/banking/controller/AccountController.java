package com.yassir.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yassir.banking.dto.BalanceDto;
import com.yassir.banking.dto.TransactionDto;
import com.yassir.banking.dto.TransferDto;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.exception.InsufficientBalanceException;
import com.yassir.banking.service.facade.IAccountFacade;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("account")
@Validated
public class AccountController {

	@Autowired
	public AccountController(final IAccountFacade accountFacade) {
		this.accountFacade = accountFacade;
	}

	private final IAccountFacade accountFacade;

	@Operation(summary = "Get the account balance", tags = "Get Balance")
	@GetMapping(path = "{accountId}")
	public ResponseEntity<BalanceDto> getBalance(@PathVariable("accountId") final Integer accountId)
			throws AccountNotFoundException {
		return new ResponseEntity<BalanceDto>(accountFacade.getBalanceByAccountNumber(accountId), HttpStatus.OK);
	}

	@Operation(summary = "Get transaction history of account", tags = "Transaction History")
	@GetMapping(path = "history/{accountId}")
	public ResponseEntity<List<TransactionDto>> getTransactionHistory(@PathVariable("accountId") final Integer accountId)
			throws AccountNotFoundException {
		return new ResponseEntity<List<TransactionDto>>(accountFacade.getTransactionHistory(accountId), HttpStatus.OK);
	}

	@Operation(summary = "Deposite amount into the account", tags = "Deposite")
	@PostMapping(path = "deposite/{accountId}")
	public ResponseEntity<BalanceDto> deposite(@PathVariable("accountId") final Integer accountId,
			@Valid @RequestBody final BalanceDto request) throws AccountNotFoundException {
		return new ResponseEntity<BalanceDto>(accountFacade.depositBalanceInAccount(accountId, request),
				HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Tranfer amount from one account to other account", tags = "Transfer")
	@PostMapping(path = "transfer/{fromAccountId}")
	public ResponseEntity<TransactionDto> transfer(@PathVariable("fromAccountId") final Integer fromAccountId,
			@Valid @RequestBody final TransferDto request)
			throws AccountNotFoundException, InsufficientBalanceException {
		System.out.println("HERE!");
		return new ResponseEntity<TransactionDto>(accountFacade.transferAmmount(fromAccountId, request),
				HttpStatus.ACCEPTED);
	}

}
