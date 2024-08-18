package com.intuit.crowdfunds.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpDTO(
    @NotBlank(message = "First name cannot be empty") 
    String firstName,

    @NotBlank(message = "Last name cannot be empty") 
    String lastName,

    @NotBlank(message = "Email cannot be empty") 
    @Email(message = "Invalid email address")
    String email,

    @NotBlank(message = "Login cannot be empty") 
    @Size(min = 5, max = 20, message = "Login must be between 5 and 20 characters")
    String login,

    @NotBlank(message = "Role cannot be empty") 
    @Pattern(regexp = "Innovator|Donor", message = "Role must be either 'Innovator' or 'Donor'")
    String role,

    @NotEmpty(message = "Password cannot be empty") 
    @Size(min = 8, message = "Password must be at least 8 characters long")
    char[] password
) { }
