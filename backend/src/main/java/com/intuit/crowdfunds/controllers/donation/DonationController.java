package com.intuit.crowdfunds.controllers.donation;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.dtos.donation.DonationRequestDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.services.donation.DonationService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/donations")
public class DonationController {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final DonationService donationService;

	@PostMapping
	public ResponseEntity<ProjectResponseDTO> makeDonation(@RequestBody @Valid DonationRequestDTO donationRequest) {
		Long projectid = donationRequest.projectId();
		String orderId = donationRequest.orderId();
		String paymentId = donationRequest.paymentId();
		logger.info("Received donation request: Project ID = {}, OrderId = {}, PaymentId = {}", projectid, orderId,paymentId);
		ProjectResponseDTO response = donationService.donate(projectid, orderId, paymentId);
		logger.info("Donation successful: Project ID = {}, OrderId = {}, PaymentId = {}", projectid, orderId,paymentId);
		return ResponseEntity.ok(response);

	}

}
