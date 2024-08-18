package com.intuit.crowdfunds.services.payment;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.dtos.payment.PaymentLinkRequestDTO;
import com.intuit.crowdfunds.entites.Order;
import com.intuit.crowdfunds.entites.Transaction;
import com.intuit.crowdfunds.entites.TransactionStatus;
import com.intuit.crowdfunds.exceptions.ResourceNotFoundException;
import com.intuit.crowdfunds.repositories.OrderRepository;
import com.intuit.crowdfunds.repositories.PaymentRepository;
import com.intuit.crowdfunds.services.UserService;
import com.intuit.crowdfunds.utility.LoggingUtil;
import com.razorpay.PaymentLink;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final IPaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final Logger logger = LoggingUtil.getLogger(getClass());

    public String createLink(String orderId) {
        UserDTO userDTO = userService.getUserDetailsFromContext();
        Optional<Order> orderOpt = orderRepository.findByOrderId(orderId);
        if (orderOpt.isEmpty()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found");
        }
        Order order = orderOpt.get();
        PaymentLinkRequestDTO paymentLinkRequestDto = new PaymentLinkRequestDTO();
        paymentLinkRequestDto.setCustomerName(userDTO.getFirstName() + " " + userDTO.getLastName());
        paymentLinkRequestDto.setOrderId(orderId);
        paymentLinkRequestDto.setPhone("7159301371");
        paymentLinkRequestDto.setEmail(userDTO.getEmail());
        paymentLinkRequestDto.setAmount(order.getAmount());

        PaymentLink paymentLink;
        try {
            paymentLink = paymentGateway.createPaymentLink(paymentLinkRequestDto);
        } catch (Exception e) {
            logger.error("Error creating payment link: {}", e.getMessage());
            throw new RuntimeException("Failed to create payment link", e);
        }

        Transaction transaction = Transaction.builder()
                .status(paymentGateway.getStatus(paymentLink.get("status")))
                .orderId(orderId)
                .transactionDate(LocalDateTime.now())
                .transactionId(paymentLink.get("id"))
                .transactionLink(paymentLink.get("short_url"))
                .build();
        
        try {
            paymentRepository.save(transaction);
        } catch (Exception e) {
            logger.error("Error saving transaction: {}", e.getMessage());
            throw new RuntimeException("Failed to save transaction", e);
        }

        return paymentLink.get("short_url");
    }

    public TransactionStatus getPaymentStatus(String transactionId, String orderId) {
        TransactionStatus status;
        try {
            status = paymentGateway.getStatus(transactionId, orderId);
        } catch (Exception e) {
            logger.error("Error retrieving payment status: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve payment status", e);
        }
        return status;
    }
}

