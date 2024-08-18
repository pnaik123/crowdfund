package com.intuit.crowdfunds.services.payment;

import java.time.LocalDate;
import java.time.ZoneId;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.intuit.crowdfunds.dtos.payment.PaymentLinkRequestDTO;
import com.intuit.crowdfunds.entites.TransactionStatus;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class RazorPayGateway implements IPaymentGateway {

	private final RazorpayClient razorpayClient;
	private static final Logger logger = LoggerFactory.getLogger(RazorPayGateway.class);

	@Override
	public PaymentLink createPaymentLink(PaymentLinkRequestDTO paymentLinkRequestDto) {
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount", paymentLinkRequestDto.getAmount());
		paymentLinkRequest.put("currency", "INR");
		paymentLinkRequest.put("expire_by",
				LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
		paymentLinkRequest.put("reference_id", paymentLinkRequestDto.getOrderId());
		paymentLinkRequest.put("description", "Payment for order no " + paymentLinkRequestDto.getOrderId());

		JSONObject customer = new JSONObject();
		customer.put("name", paymentLinkRequestDto.getCustomerName());
		customer.put("contact", paymentLinkRequestDto.getPhone());
		customer.put("email", paymentLinkRequestDto.getEmail());
		paymentLinkRequest.put("customer", customer);

		JSONObject notes = new JSONObject();
		notes.put("policy_name", "Jeevan Bima");
		paymentLinkRequest.put("notes", notes);
		paymentLinkRequest.put("callback_url", "http://localhost:3000/paymentRedirect");
		paymentLinkRequest.put("callback_method", "get");

		try {
			PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
			return paymentLink;
		} catch (RazorpayException e) {
			logger.error("Failed to create payment link: {}", e.getMessage());
			throw new RuntimeException("Failed to create payment link", e);
		}
	}

	@Override
	public TransactionStatus getStatus(String paymentId, String orderId) {
		try {
			Payment payment = razorpayClient.payments.fetch(paymentId);
			String statusType = payment.get("status");
			return mapStatusToTransactionStatus(statusType);
		} catch (RazorpayException e) {
			logger.error("Unable to fetch the payment details: {}", e.getMessage());
			throw new RuntimeException("Unable to fetch the payment details", e);
		}
	}

	@Override
	public TransactionStatus getStatus(String statusType) {
		return mapStatusToTransactionStatus(statusType);
	}

	private TransactionStatus mapStatusToTransactionStatus(String statusType) {
		return switch (statusType) {
		case "captured" -> TransactionStatus.SUCCESS;
		case "failed" -> TransactionStatus.FAILURE;
		default -> TransactionStatus.INITIATED;
		};
	}

	public Order createOrder(Double amount) {
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "receipt#1");

		try {
			return razorpayClient.orders.create(orderRequest);
		} catch (RazorpayException e) {
			logger.error("Failed to create Razorpay order: {}", e.getMessage());
			throw new RuntimeException("Failed to create Razorpay order", e);
		}
	}
}
