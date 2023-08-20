package com.yassir.banking.service;

import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.exception.CustomerNotFoundException;

public interface ICustomerService {

	public CustomerEntity getCustomerById(final Integer id) throws CustomerNotFoundException;

	public CustomerEntity createCustomer(final String fullname);

}