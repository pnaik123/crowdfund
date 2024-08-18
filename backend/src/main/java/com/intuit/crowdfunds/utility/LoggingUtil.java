package com.intuit.crowdfunds.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}

