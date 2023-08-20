package com.yassir.banking.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.yassir.banking.dto.TransactionDto;
import com.yassir.banking.entity.AccountEntity;
import com.yassir.banking.entity.TransactionEntity;

public class TransactionMapper {
	public static TransactionDto toTransactionDto(final TransactionEntity transactionEntity) {

		TransactionDto dto = new TransactionDto(transactionEntity.getId(), transactionEntity.getToAccount().getId(),
				transactionEntity.getType(), transactionEntity.getTransactionDate(), transactionEntity.getAmount());

		AccountEntity accountEntity = transactionEntity.getFromAccount();

		if (null != accountEntity) {
			dto.setFromAccountNumber(accountEntity.getId());
		}

		return dto;
	}

	public static List<TransactionDto> toTransactionDto(final List<TransactionEntity> transactionEntities) {
		return transactionEntities.stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
	}

}
