package com.intuit.crowdfunds.config;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.intuit.crowdfunds.filters.JwtAuthFilter;
import com.intuit.crowdfunds.utility.LoggingUtil;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	@Value("${endpoint.login}")
	private String login;
	@Value("${endpoint.register}")
	private String register;

	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	private final UserAuthenticationProvider userAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Initializing the security config, Open endpoints are {} and {}", login, register);
		http.exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
				.addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.POST, login, register)
						.permitAll().anyRequest().authenticated());
		return http.build();
	}
}
