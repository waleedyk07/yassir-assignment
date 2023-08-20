package com.yassir.banking.dto.mapper;

import com.yassir.banking.dto.AccountDto;
import com.yassir.banking.entity.AccountEntity;

public class AccountMapper {
	public static AccountDto toAccountDto(final AccountEntity accountEntity) {
		return new AccountDto(accountEntity.getId(), accountEntity.getTitle());
	}
}
