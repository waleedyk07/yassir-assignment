package com.yassir.banking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerDto {

	private Integer customerId;
	
	@NotBlank(message = "customerFullName cannot be empty")
	private String customerFullName;

	public CustomerDto(final Integer customerId, final String customerFullName) {
		this.customerId = customerId;
		this.customerFullName = customerFullName;
	}

	@Override
	public String toString() {
		return "CustomerDto [customerId=" + customerId + ", customerFullName=" + customerFullName + "]";
	}

}
