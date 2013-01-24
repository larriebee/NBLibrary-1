package com.netbuilder.nblibrary.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidatorService implements ValidatorServiceTemplate {

	private static final String TEXT_PATTERN = "^[a-zA-Z\\'\\-\\&\\.\\s]+$";

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	@Override
	public boolean validateEmail(String email) {
		return validate(EMAIL_PATTERN, email);
	}

	/*
	 * Will be used to validate name, title and author.
	 */
	@Override
	public boolean validateText(String text) {
		return validate(TEXT_PATTERN, text);
	}

	@Override
	public boolean validateDate(String date) {
		return validate(DATE_PATTERN, date);
	}

	private boolean validate(String regex, String operand) {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(operand);
		return matcher.matches();
	}
}
