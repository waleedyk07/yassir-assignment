package com.yassir.banking.dto.mapper;

import com.yassir.banking.dto.BalanceDto;
import com.yassir.banking.entity.BalanceEntity;

public class BalanceMapper {
	public static BalanceDto toBalanceDto(final BalanceEntity balanceEntity) {
		return new BalanceDto(balanceEntity.getCurrentBalance());
	}
}
