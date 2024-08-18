package com.intuit.crowdfunds.services.payment;

import com.intuit.crowdfunds.dtos.payment.PaymentLinkRequestDTO;
import com.intuit.crowdfunds.entites.TransactionStatus;
import com.razorpay.Order;
import com.razorpay.PaymentLink;

public interface IPaymentGateway {
	
	PaymentLink createPaymentLink(PaymentLinkRequestDTO paymentLinkRequestDto);
    TransactionStatus getStatus(String paymentId, String orderId);
    TransactionStatus getStatus(String statusType);
    Order createOrder(Double amount);

}
