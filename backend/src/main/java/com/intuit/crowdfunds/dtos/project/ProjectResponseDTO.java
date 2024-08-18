package com.intuit.crowdfunds.dtos.project;

public record ProjectResponseDTO(Long id, String title, String description, int goalAmount, int currentAmount,
		String status, String createdAt, String updatedAt) {

}
