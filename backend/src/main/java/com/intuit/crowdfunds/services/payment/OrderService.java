package com.intuit.crowdfunds.services.payment;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.intuit.crowdfunds.entites.Currency;
import com.intuit.crowdfunds.repositories.OrderRepository;
import com.intuit.crowdfunds.utility.LoggingUtil;
import com.razorpay.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final IPaymentGateway paymentGateway;
	private final OrderRepository orderRepository;

	public String createOrder(Double amount) {
		try {
			logger.info("Creating order with amount: {}", amount);

			Order order = paymentGateway.createOrder(amount);
			String orderId = order.get("id");
			com.intuit.crowdfunds.entites.Order localDbOrder = new com.intuit.crowdfunds.entites.Order();
			localDbOrder.setOrderId(orderId);
			localDbOrder.setReceipt(order.get("receipt"));
			localDbOrder.setAmount(amount);
			localDbOrder.setCurrency(Currency.INR);
			localDbOrder.setStatus(order.get("status"));

			logger.info("Order created with ID: {}", orderId);
            try {
                orderRepository.save(localDbOrder);
            } catch (Exception e) {
                logger.error("Error saving order to database: {}", e.getMessage());
                // Optionally, handle compensation or alerting here
                //Add retry in the case of failure
                throw new RuntimeException("Order could not be saved to database", e);
            }

			logger.info("Order created and saved with ID: {}", orderId);
			return order.get("id");
		} catch (Exception e) {
			logger.error("Error creating order: {}", e.getMessage());
			// Handle compensation or notify external systems if needed
			throw new RuntimeException("Order creation failed", e);
		}
	}

}
