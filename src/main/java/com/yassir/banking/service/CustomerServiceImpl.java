package com.yassir.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassir.banking.entity.CustomerEntity;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private final CustomerRepository customerRepo;

	@Autowired
	public CustomerServiceImpl(final CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Override
	public CustomerEntity createCustomer(final String fullname) {
		CustomerEntity customer = new CustomerEntity();
		customer.setFullname(fullname);
		return customerRepo.save(customer);
	}

	@Override
	public CustomerEntity getCustomerById(final Integer id) throws CustomerNotFoundException {
		Optional<CustomerEntity> _customer = customerRepo.findById(id);
		if (_customer.isEmpty())
			throw new CustomerNotFoundException("Customer not found");
		else
			return _customer.get();
	}

}
