package com.yassir.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yassir.banking.entity.BalanceEntity;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Integer> {
	public BalanceEntity findByAccountId(final Integer accountNumber);
}
