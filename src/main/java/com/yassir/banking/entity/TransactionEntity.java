package com.yassir.banking.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.yassir.banking.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "transaction")
public class TransactionEntity {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "bank_transaction_entity_sequence_generator"
	)
	@SequenceGenerator(
			name = "bank_transaction_entity_sequence_generator",
			sequenceName = "bank_transaction_entity_sequence",
			initialValue = 3000,
			allocationSize = 1
	)
	@Column(updatable = false)
	private Integer id;
	
	private double amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AccountEntity fromAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AccountEntity toAccount;
	
	@Enumerated(EnumType.ORDINAL)
	private TransactionType type;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	
	@PrePersist
	protected void onCreate() {
		this.transactionDate = new Timestamp(System.currentTimeMillis());
	}
}
