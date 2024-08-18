package com.intuit.crowdfunds.services;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.intuit.crowdfunds.dtos.CredentialsDTO;
import com.intuit.crowdfunds.dtos.SignUpDTO;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.entites.User;
import com.intuit.crowdfunds.exceptions.BadRequestException;
import com.intuit.crowdfunds.exceptions.ResourceNotFoundException;
import com.intuit.crowdfunds.exceptions.UnauthorizedException;
import com.intuit.crowdfunds.mappers.UserMapper;
import com.intuit.crowdfunds.repositories.UserRepository;
import com.intuit.crowdfunds.utility.LoggingUtil;

import lombok.RequiredArgsConstructor;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.Optional;
import static com.intuit.crowdfunds.constants.Constants.ErrorMessage.*;

@RequiredArgsConstructor
@Service
public class UserService {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	public UserDTO login(CredentialsDTO credentialsDto) {
		logger.info("Login attempt for user: {}", credentialsDto.login());
		User user = userRepository.findByLogin(credentialsDto.login()).orElseThrow(() -> {
			logger.error("Login failed for user: {} - User not found", credentialsDto.login());
			return new ResourceNotFoundException(UNKNOWN_USER);
		});

		if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
			UserDTO userDto = userMapper.toUserDto(user);
			logger.info("Login successful for user: {}", user.getFirstName());
			return userDto;
		} else {
			logger.error("Login failed for user: {} - Invalid password", credentialsDto.login());
			throw new BadRequestException(INVALID_PASSWORD);
		}
	}

	public UserDTO register(SignUpDTO userDto) {
		logger.info("Registration attempt for user: {}", userDto.login());

		Optional<User> optionalUser = userRepository.findByLogin(userDto.login());

		if (optionalUser.isPresent()) {
			logger.error("Registration failed for user: {} - Login already exists", userDto.login());
			throw new BadRequestException(LOGIN_ALREADY_EXISTS);
		}

		User user = userMapper.signUpToUser(userDto);
		user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));
		LocalDateTime currentDateTime = LocalDateTime.now();
		user.setCreatedAt(currentDateTime);
		user.setUpdatedAt(currentDateTime);
		User savedUser = userRepository.save(user);

		UserDTO userDtoResult = userMapper.toUserDto(savedUser);
		logger.info("Registration successful for user: {}", userDtoResult.getFirstName());

		return userDtoResult;
	}

	public UserDTO findByLogin(String login) {
		logger.info("Finding user by login: {}", login);

		User user = userRepository.findByLogin(login).orElseThrow(() -> {
			logger.error("User not found for login: {}", login);
			return new ResourceNotFoundException(UNKNOWN_USER);
		});

		UserDTO userDto = userMapper.toUserDto(user);
		logger.info("User found for login: {}", login);

		return userDto;
	}

	public UserDTO getUserDetailsFromContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDTO) {
				UserDTO userDetails = (UserDTO) principal;
				logger.info("User details retrieved from context: {}", userDetails.getFirstName());
				return userDetails;
			} else {
				logger.error("User details retrieval failed - Principal is not an instance of UserDTO");
				throw new ResourceNotFoundException(UNKNOWN_USER);
			}
		}
		logger.error("User cannot be authenticated - Security context is not authenticated");
		throw new UnauthorizedException(USER_CANNOT_BE_AUTHENTICATED);
	}

}
