package com.admission.utils;

import java.util.regex.Pattern;

public class Validation {
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	
	public static Boolean emailValidation(String input) {
		return Pattern.matches(EMAIL_REGEX, input);
	}

}
