package com.intuit.crowdfunds.dtos;

import org.springframework.http.HttpStatus;

public record ErrorDTO (String message, HttpStatus status) { }
