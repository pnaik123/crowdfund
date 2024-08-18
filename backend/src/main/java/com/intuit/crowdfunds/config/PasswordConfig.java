package com.intuit.crowdfunds.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.intuit.crowdfunds.utility.LoggingUtil;

@Component
public class PasswordConfig {

	private final Logger logger = LoggingUtil.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Initializing BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }
}
