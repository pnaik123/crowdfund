package com.intuit.crowdfunds.controllers.payment;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.entites.TransactionStatus;
import com.intuit.crowdfunds.services.payment.PaymentService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/payment")
@Validated
public class PaymentController {
	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final PaymentService paymentService;

	@PostMapping("/createLink")
	public ResponseEntity<String> createPaymentLink(
			@RequestParam(name="orderId") @NotBlank(message = "Order ID cannot be blank")  String orderId) {
		logger.info("Received request to create payment link for order ID: {}", orderId);
		String paymentLink = paymentService.createLink(orderId);
		logger.info("Payment link created successfully for order ID: {}", orderId);
		return ResponseEntity.ok(paymentLink);
	}

	@GetMapping("/getPaymentStatus")
	public ResponseEntity<TransactionStatus> getPaymentStatus(
			@RequestParam("paymentId") @NotBlank(message = "Payment ID cannot be blank")  String paymentId,
			@RequestParam @NotBlank(message = "Order ID cannot be blank") String orderId) {
		logger.info("Received request to create payment status for order ID: {} and Payment ID:{}", orderId, paymentId);
		TransactionStatus transactionStatus = paymentService.getPaymentStatus(paymentId, orderId);
		logger.info("Recieved Payment status  successfully for order ID: {} and Payment ID:{}", orderId, paymentId);
		return ResponseEntity.ok(transactionStatus);
	}

}
