package com.intuit.crowdfunds.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.config.UserAuthenticationProvider;
import com.intuit.crowdfunds.dtos.CredentialsDTO;
import com.intuit.crowdfunds.dtos.SignUpDTO;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.services.UserService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final UserService userService;
	private final UserAuthenticationProvider userAuthenticationProvider;

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentialsDto) {
		logger.info("Login attempt for user: {}", credentialsDto.login());
		UserDTO userDto = userService.login(credentialsDto);
		userDto.setToken(userAuthenticationProvider.createToken(userDto));
		logger.info("Login successful for user: {}", credentialsDto.login());
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody @Valid SignUpDTO user) {
		logger.info("Registration attempt for user: {}", user.firstName());
		UserDTO createdUser = userService.register(user);
		createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
		logger.info("Registration successful for user: {}", createdUser.getFirstName());
		return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
	}

}
