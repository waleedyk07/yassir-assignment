package com.yassir.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yassir.banking.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
	
	@Query("select t from transaction t where t.toAccount.id = ?1 or t.fromAccount.id = ?1")
	public List<TransactionEntity> findByFromOrToAccountId(final Integer accountNumer);
}
