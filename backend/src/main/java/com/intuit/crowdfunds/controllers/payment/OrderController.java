package com.intuit.crowdfunds.controllers.payment;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.exceptions.BadRequestException;
import com.intuit.crowdfunds.services.payment.OrderService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
@Validated
public class OrderController {
	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<String> createOrder(
			@RequestParam(name = "amount") Double amount) {
		logger.info("Received create order request with amount: {}", amount);
		// Check for valid amount directly in the method
		if (amount == null || amount <= 0) {
			logger.error("Invalid amount: {}", amount);
			throw new BadRequestException("Invalid amont");
		}
		String orderId = orderService.createOrder(amount);
		logger.info("Order created successfully with ID: {}", orderId);
		return ResponseEntity.ok(orderId);
	}

}
