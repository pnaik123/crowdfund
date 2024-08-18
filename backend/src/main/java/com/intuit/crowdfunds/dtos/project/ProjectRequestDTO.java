package com.intuit.crowdfunds.dtos.project;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProjectRequestDTO(@NotBlank(message = "Title cannot be empty") String title,
		@NotBlank(message = "Description cannot be empty") String description,
		@NotNull(message = "Goal amount must be specified") @Positive(message = "Goal amount must be positive") Double goalAmount,
		@NotNull(message = "Category must be specified") String category,
		@NotNull(message = "Category must be specified")String subcategory,
		@NotNull(message = "Date must be specifed") LocalDateTime launchDate,
		@NotNull(message = "Bank details must be specified") String bankDetails) {
}