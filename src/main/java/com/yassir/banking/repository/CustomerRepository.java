package com.yassir.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yassir.banking.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	
	public CustomerEntity getByFullname(final String fullname);
}
