package com.intuit.crowdfunds.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.intuit.crowdfunds.constants.Constants;
import com.intuit.crowdfunds.utility.LoggingUtil;

import java.util.Arrays;

@Configuration
@EnableWebMvc
public class WebConfig {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	@Value("${origin.allowed}")
	private String allowedOrgin;

	@Bean
	public FilterRegistrationBean corsFilter() {
		logger.info("Initializing the WebConfig, CORS, Allowed orgins: {}", allowedOrgin);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(allowedOrgin);
		config.setAllowedHeaders(
				Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()));
		config.setMaxAge(Constants.WebConfig.MAX_AGE);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

		// should be set order to -100 because we need to CorsFilter before
		// SpringSecurityFilter
		bean.setOrder(Constants.WebConfig.CORS_FILTER_ORDER);
		return bean;
	}
}
