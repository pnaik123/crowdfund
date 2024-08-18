package com.intuit.crowdfunds.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CredentialsDTO(
    @NotBlank(message = "Login cannot be empty") 
    String login,

    @NotEmpty(message = "Password cannot be empty") 
    char[] password
) { }
