package com.intuit.crowdfunds.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

	@UtilityClass
	public class User {
		public static final String FIRST_NAME = "firstName";
		public static final String LAST_NAME = "lastName";
		public static final String ROLE = "role";
		public static final String EMAIL = "email";
	}

	@UtilityClass
	public class WebConfig {
		public static final Long MAX_AGE = 3600L;
		public static final int CORS_FILTER_ORDER = -102;
	}

	@UtilityClass
	public class Auth {
		public static final String BEARER = "Bearer";
	}

	@UtilityClass
	public class STATUS {
		public static final String ACTIVE = "Active";
	}

	@UtilityClass
	public class ErrorMessage {

		public static final String USER_CANNOT_BE_AUTHENTICATED = "User cannot be authenticated";

		public static final String LOGIN_ALREADY_EXISTS = "Login already exists";

		public static final String INVALID_PASSWORD = "Invalid password";

		public static final String UNKNOWN_USER = "Unknown user";

	}
}
