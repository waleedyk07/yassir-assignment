package com.yassir.banking.dto.mapper;

import com.yassir.banking.dto.CustomerDto;
import com.yassir.banking.entity.CustomerEntity;

public class CustomerMapper {
	public static CustomerDto toCustomerDto(final CustomerEntity customerEntity) {
		return new CustomerDto(customerEntity.getId(), customerEntity.getFullname());
	}
}
