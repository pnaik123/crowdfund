package com.intuit.crowdfunds.services.donation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.entites.Donation;
import com.intuit.crowdfunds.entites.Order;
import com.intuit.crowdfunds.entites.Project;
import com.intuit.crowdfunds.entites.Transaction;
import com.intuit.crowdfunds.entites.TransactionStatus;
import com.intuit.crowdfunds.entites.User;
import com.intuit.crowdfunds.exceptions.BadRequestException;
import com.intuit.crowdfunds.exceptions.ResourceNotFoundException;
import com.intuit.crowdfunds.mappers.ProjectMapper;
import com.intuit.crowdfunds.repositories.DonationRepository;
import com.intuit.crowdfunds.repositories.OrderRepository;
import com.intuit.crowdfunds.repositories.PaymentRepository;
import com.intuit.crowdfunds.repositories.ProjectRepository;
import com.intuit.crowdfunds.repositories.UserRepository;
import com.intuit.crowdfunds.services.UserService;
import com.intuit.crowdfunds.services.payment.PaymentService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DonationService {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final DonationRepository donationRepository;
	private final ProjectMapper mapper;
	private final PaymentService paymentService;
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;

	@Transactional
	public ProjectResponseDTO donate(Long projectId, String orderId, String paymentId) {
		logger.info("Starting donation process. Project ID: {}, OrderId: {}, paymentId = {}", projectId, orderId,
				paymentId);

		UserDTO userDto = userService.getUserDetailsFromContext();
		Optional<User> userOpt = userRepository.findByLogin(userDto.getLogin());
		if (!userOpt.isPresent()) {
			logger.error("Invalid user: {}", userDto.getLogin());
			throw new BadRequestException("Invalid User");
		}

		Optional<Project> projectOpt = projectRepository.findById(projectId);
		if (!projectOpt.isPresent()) {
			logger.error("Project not found: {}", projectId);
			throw new BadRequestException("Project not found: " + projectId);
		}

		Optional<Transaction> transactionOpt = paymentRepository.findByOrderId(orderId);
		if (transactionOpt.isEmpty()) {
			logger.error("Transaction not found for order ID: {}", orderId);
			throw new ResourceNotFoundException("Payment not found");
		}

		Optional<Order> orderOpt = orderRepository.findByOrderId(orderId);
		if (orderOpt.isEmpty()) {
			logger.error("Order not found with ID: {}", orderId);
			throw new ResourceNotFoundException("Order not found");
		}

		Order order = orderOpt.get();

		Project project = projectOpt.get();
		User user = userOpt.get();
		Donation donation = Donation.builder().createdAt(LocalDateTime.now()).project(project).donor(user).order(order)
				.build();

		try {
			donationRepository.save(donation);
		} catch (Exception e) {
			logger.error("Error cretaig donation: {}", e.getMessage());
			throw new RuntimeException("Failed to add donation entry", e);
		}

		TransactionStatus status = paymentService.getPaymentStatus(paymentId, String.valueOf(orderId));

		Transaction transaction = transactionOpt.get();
		transaction.setStatus(status);
		transaction.setTransactionId(paymentId);

		try {
			paymentRepository.save(transaction);
		} catch (Exception e) {
			logger.error("Error updating transaction status: {}", e.getMessage());
			throw new RuntimeException("Failed to update transaction status", e);
		}

		project.setCurrentAmount(project.getCurrentAmount() + orderOpt.get().getAmount());
		project.setUpdatedAt(LocalDateTime.now());
		try {
			projectRepository.save(project);
		} catch (Exception e) {
			logger.error("Error updating project amount: {}", e.getMessage());
			throw new RuntimeException("Failed to update project amount", e);
		}
		logger.info("Donation processed successfully. Project ID: {}, OrderId: {}, PaymentId User: {}", projectId,
				orderId, paymentId, userDto.getLogin());
		return mapper.toDto(project);
	}

	public List<Donation> getAllDonations() {
		logger.info("Fetching all donations.");
		List<Donation> donations = donationRepository.findAll();
		logger.info("Retrieved {} donations.", donations.size());
		return donations;
	}

	public Donation getProject(Long donationId) {
		logger.info("Fetching donation with ID: {}", donationId);
		Optional<Donation> donationOpt = donationRepository.findById(donationId);
		if (donationOpt.isPresent()) {
			Donation donation = donationOpt.get();
			logger.info("Donation found: {}", donation);
			return donation;
		} else {
			logger.error("Donation not found: {}", donationId);
			throw new ResourceNotFoundException("Donation not found:" + donationId);
		}
	}

}
