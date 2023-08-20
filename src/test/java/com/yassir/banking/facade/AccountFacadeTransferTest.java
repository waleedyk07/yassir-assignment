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

import com.yassir.banking.dto.TransferDto;
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
public class AccountFacadeTransferTest {

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
	@DisplayName("Transaction history is being maintained with correct transaction type.")
	@Order(1)
	public void depositTest() throws AccountNotFoundException, InsufficientBalanceException {
		TransferDto dto = new TransferDto(13, 10);

		when(accountService.getAccountById(13)).thenReturn(mockAccount);
		when(balanceService.depositBalanceForAccount(10, mockAccount)).thenReturn(mockBalance);
		when(balanceService.deductBalanceForAccount(10, mockAccount)).thenReturn(mockBalance)
				.thenThrow(new InsufficientBalanceException("Insufficient balance."));
		when(transactionService.createTransactionLog(mockAccount, mockAccount, TransactionType.ACCOUNT_TRANSER, 10))
				.thenReturn(mockTransaction);

		accountFacade.transferAmmount(13, dto);

		verify(transactionService, times(1)).createTransactionLog(mockAccount, mockAccount,
				TransactionType.ACCOUNT_TRANSER, 10);
		verify(balanceService, times(1)).deductBalanceForAccount(10, mockAccount);
		verify(balanceService, times(1)).depositBalanceForAccount(10, mockAccount);
	}

	@Test
	@DisplayName("Balance is not transferred to account when insufficient balance.")
	@Order(2)
	public void insufficientBalanceTest() throws AccountNotFoundException, InsufficientBalanceException {
		TransferDto dto = new TransferDto(13, 10);

		assertThrows(InsufficientBalanceException.class, () -> accountFacade.transferAmmount(13, dto));

		// Transaction history is not created
		verify(transactionService, times(0)).createTransactionLog(mockAccount, mockAccount,
				TransactionType.ACCOUNT_TRANSER, 10);
		
		// Balance is not deducted and deposited
		verify(balanceService, times(0)).deductBalanceForAccount(10, mockAccount);
		verify(balanceService, times(0)).depositBalanceForAccount(10, mockAccount);
	}

}
