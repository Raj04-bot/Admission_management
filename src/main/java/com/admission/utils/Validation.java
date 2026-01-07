package com.admission.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.admission.exception.BusinessException;

public class Validation {
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	
	public static Boolean emailValidation(String input) {
		return Pattern.matches(EMAIL_REGEX, input);
	}
	
	public static LocalDate parseDateOfBirth(String dob) {
	    try {
	        return LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);
	    } catch (DateTimeParseException e) {
	        throw new BusinessException("Invalid date format. Use yyyy-MM-dd");
	    }
	}

}
