package com.intuit.crowdfunds.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intuit.crowdfunds.utility.LoggingUtil;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class PaymentGatewayConfig {
	
	private final Logger logger = LoggingUtil.getLogger(getClass());

	@Value("${razorpay.client.secret}")
	private String secret;

	@Value("${razorpay.client.id}")
	private String id;
	

	@Bean
	public RazorpayClient getRazorpayClient() {
		try {
			logger.info("Initializing the Razorpay Client GW ");
			return new RazorpayClient(id, secret);
		} catch (RazorpayException e) {
			System.out.println("Unable to create client for razorpay");
			throw new RuntimeException("Failed to instantiate razorpay");
		}
	}

}
