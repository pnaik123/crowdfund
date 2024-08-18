package com.intuit.crowdfunds.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseModel {
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "transaction_id")
    private String transactionId;
    
	@Column(name = "transaction_link")
    private String transactionLink;
	
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
}
