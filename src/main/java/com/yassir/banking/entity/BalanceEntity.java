package com.yassir.banking.entity;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "balance")
public class BalanceEntity {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "bank_account_balance_entity_sequence_generator"
	)
	@SequenceGenerator(
			name = "bank_account_balance_entity_sequence_generator",
			sequenceName = "bank_account_balance_entity_sequence",
			initialValue = 2000,
			allocationSize = 1
	)
	@Column(updatable = false)
	private Integer id;
	
	private double currentBalance;
	
	@OneToOne(fetch = FetchType.LAZY)
	private AccountEntity account;
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	
	@PrePersist
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTime = new Timestamp(System.currentTimeMillis());
	}
	
	
}
