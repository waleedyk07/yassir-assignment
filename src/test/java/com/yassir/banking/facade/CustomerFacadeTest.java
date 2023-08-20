package com.yassir.banking.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.yassir.banking.dto.AccountDto;
import com.yassir.banking.dto.OpenAccountDto;
import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.enums.TransactionType;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.service.IAccountService;
import com.yassir.banking.service.IBalanceService;
import com.yassir.banking.service.ICustomerService;
import com.yassir.banking.service.ITransactionService;
import com.yassir.banking.service.facade.CustomerFacadeImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerFacadeTest {

	// Class to test
	@InjectMocks
	private CustomerFacadeImpl customerFacade;

	// Mock Dependencies ------------
	@Mock
	private ICustomerService customerService;

	@Mock
	private IAccountService accountService;

	@Mock
	private IBalanceService balanceService;

	@Mock
	private ITransactionService transactionService;
	// Mock Dependencies ------------

	private CustomerEntity mockCustomer;
	private AccountEntity mockAccount;

	@BeforeAll
	public void setup() throws CustomerNotFoundException {
		mockCustomer = new CustomerEntity();
		mockCustomer.setId(12);
		mockCustomer.setFullname("Any Name");

		mockAccount = new AccountEntity();
		mockAccount.setId(13);
		mockAccount.setTitle("Test Account");
		mockAccount.setCustomer(mockCustomer);
		
	}

	@Test
	@DisplayName("Account is created and initial deposit is logged.")
	@Order(1)
	public void createAccountTest() throws CustomerNotFoundException {
		when(customerService.getCustomerById(12)).thenReturn(mockCustomer).thenThrow(new CustomerNotFoundException("Customer not found"));
		when(accountService.createForCustomer("Test Account", mockCustomer)).thenReturn(mockAccount);

		OpenAccountDto dto = new OpenAccountDto("Test Account", 100);


		AccountDto expected = new AccountDto(13, "Test Account");
		AccountDto result = customerFacade.createAccount(12, dto);

		assertEquals(expected.getAccountNumber(), result.getAccountNumber());
		assertEquals(expected.getAccountTitle(), result.getAccountTitle());

		// Verify how many times method called
		verify(accountService, times(1)).createForCustomer("Test Account", mockCustomer);
		verify(transactionService, times(1)).createTransactionLog(null, mockAccount, TransactionType.INITIAL_DEPOSIT,
				100);
	}

	@Test
	@DisplayName("Account is not created for CustomerNotFoundException")
	@Order(2)
	public void customerNotFouncTest() throws CustomerNotFoundException {
		OpenAccountDto dto = new OpenAccountDto("Test Account", 100);
		assertThrows(CustomerNotFoundException.class, () -> customerFacade.createAccount(12, dto));

		// Verify if method is not called
		verify(accountService, times(0)).createForCustomer("Test Account", mockCustomer);
	}

}
