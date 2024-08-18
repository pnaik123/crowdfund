package com.intuit.crowdfunds.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel {
	@OneToOne(mappedBy = "order")
	private Donation donation;
	private String orderId;
	private String receipt;
	private Double amount;
	private String status;
    @Enumerated(EnumType.STRING)
    private Currency currency;

}
