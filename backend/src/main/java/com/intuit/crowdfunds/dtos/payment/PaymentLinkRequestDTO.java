package com.intuit.crowdfunds.dtos.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentLinkRequestDTO {
	
    private String orderId;
    private String customerName;
    private String email;
    private double amount;
    private String phone;

}
