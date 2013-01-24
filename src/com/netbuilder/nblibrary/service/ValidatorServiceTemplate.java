package com.netbuilder.nblibrary.service;

public interface ValidatorServiceTemplate {

	public boolean validateEmail(String email);

	/*
	 * Will be used to validate name and author.
	 */
	public boolean validateText(String text);

	public boolean validateDate(String date);

}
