package com.yassir.banking.facade;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yassir.banking.dto.BalanceDto;
import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.BalanceEntity;
import com.yassir.banking.entity.TransactionEntity;
import com.yassir.banking.enums.TransactionType;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.exception.InsufficientBalanceException;
import com.yassir.banking.service.IAccountService;
import com.yassir.banking.service.IBalanceService;
import com.yassir.banking.service.ITransactionService;
import com.yassir.banking.service.facade.AccountFacadeImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class AccountFacadeDepositeTest {

	// Class to test
	@InjectMocks
	private AccountFacadeImpl accountFacade;

	// Mock Dependencies ------------
	@Mock
	private IBalanceService balanceService;

	@Mock
	private IAccountService accountService;

	@Mock
	private ITransactionService transactionService;
	// Mock Dependencies ------------

	private AccountEntity mockAccount;
	private BalanceEntity mockBalance;
	private TransactionEntity mockTransaction;

	@BeforeAll
	public void setup() throws CustomerNotFoundException {
		mockAccount = new AccountEntity();
		mockAccount.setId(13);
		mockAccount.setTitle("Test Account");

		mockBalance = new BalanceEntity();
		mockBalance.setAccount(mockAccount);
		mockBalance.setCurrentBalance(100);

		mockTransaction = new TransactionEntity();
		mockTransaction.setId(23);
		mockTransaction.setToAccount(mockAccount);
		mockTransaction.setFromAccount(mockAccount);
		mockTransaction.setAmount(123);
		mockTransaction.setType(TransactionType.ACCOUNT_TRANSER);
	}

	@Test
	@DisplayName("Deposite Transaction is logged.")
	@Order(1)
	public void depositTest() throws AccountNotFoundException, InsufficientBalanceException {
		BalanceDto dto = new BalanceDto(100);

		when(accountService.getAccountById(13))
		.thenReturn(mockAccount)
		.thenThrow(new AccountNotFoundException("account not found"));
		
		when(balanceService.depositBalanceForAccount(100, mockAccount))
		.thenReturn(mockBalance);
		
		when(transactionService.createTransactionLog(null, mockAccount, TransactionType.DEPOSIT, 100))
		.thenReturn(mockTransaction);

		accountFacade.depositBalanceInAccount(13, dto);

		verify(transactionService, times(1)).createTransactionLog(null, mockAccount, TransactionType.DEPOSIT, 100);
	}

	@Test
	@DisplayName("Amount is not deposite in correct account.")
	@Order(3)
	public void incorrectAccountDeposit() throws AccountNotFoundException {
		assertThrows(AccountNotFoundException.class,
				() -> accountFacade.depositBalanceInAccount(13, new BalanceDto(100)));
		verify(transactionService, times(0)).createTransactionLog(null, mockAccount, TransactionType.DEPOSIT, 100);
	}

}
