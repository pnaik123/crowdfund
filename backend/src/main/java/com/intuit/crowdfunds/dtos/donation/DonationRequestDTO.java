package com.intuit.crowdfunds.dtos.donation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DonationRequestDTO(
		@NotNull(message = "Project ID cannot be null") Long projectId,
		@NotBlank(message = "orderId connot be blank") String orderId,
		@NotBlank(message = "paymentId cannot be blank")String paymentId) {

}
