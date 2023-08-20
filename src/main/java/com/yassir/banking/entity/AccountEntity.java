package com.yassir.banking.entity;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "account")
public class AccountEntity {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "bank_account_entity_sequence_generator"
	)
	@SequenceGenerator(
			name = "bank_account_entity_sequence_generator",
			sequenceName = "bank_account_entity_sequence",
			initialValue = 2000,
			allocationSize = 1
	)
	@Column(updatable = false)
	private Integer id;
	
	private String title;
		
	@ManyToOne(fetch = FetchType.LAZY)
	private CustomerEntity customer;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@PrePersist
	protected void onCreate() {
		this.createDate = new Timestamp(System.currentTimeMillis());
	}
}
