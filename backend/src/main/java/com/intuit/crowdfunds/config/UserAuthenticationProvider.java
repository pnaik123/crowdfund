package com.intuit.crowdfunds.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.intuit.crowdfunds.constants.Constants;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.services.UserService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${token.expiry.time:3600}")
	private long tokenExpiryTime;

	private final UserService userService;

	@PostConstruct
	protected void init() {
		logger.info("Initializing the UserAuthenticationProvider...");
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		logger.info("Secret key has been encoded.");
		logger.info("UserAuthenticationProvider initialized successfully.");
	}

	public String createToken(UserDTO user) {
		logger.info("Creating token for user: {}", user.getLogin());
		Date now = new Date();
		Date validity = new Date(now.getTime() + tokenExpiryTime);

		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create()
				.withSubject(user.getLogin())
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withClaim(Constants.User.FIRST_NAME, user.getFirstName())
				.withClaim(Constants.User.LAST_NAME, user.getLastName())
				.withClaim(Constants.User.ROLE, user.getRole().toString())
				.withClaim(Constants.User.EMAIL, user.getEmail())
				.sign(algorithm);

		logger.info("Token created successfully for user: {}", user.getLogin());
		return token;
	}

	public Authentication validateToken(String token) {
		logger.info("Validating token...");
		Algorithm algorithm = Algorithm.HMAC256(secretKey);

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decoded = verifier.verify(token);

		UserDTO user = UserDTO.builder().login(decoded.getSubject())
				.firstName(decoded.getClaim(Constants.User.FIRST_NAME).asString())
				.lastName(decoded.getClaim(Constants.User.LAST_NAME).asString())
				.role(decoded.getClaim(Constants.User.ROLE).toString()).build();

		logger.info("Token validated successfully for user: {}", user.getLogin());
		return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
	}

	public Authentication validateTokenStrongly(String token) {
		logger.info("Strongly validating token...");
		Algorithm algorithm = Algorithm.HMAC256(secretKey);

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decoded = verifier.verify(token);

		UserDTO user = userService.findByLogin(decoded.getSubject());

		logger.info("Token strongly validated successfully for user: {}", user.getLogin());
		return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
	}
}
