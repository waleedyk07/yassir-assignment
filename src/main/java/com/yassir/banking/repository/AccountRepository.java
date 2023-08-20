package com.yassir.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yassir.banking.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
	public List<AccountEntity> findByCustomerId(final Integer customerId);
}
