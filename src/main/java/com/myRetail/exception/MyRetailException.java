package com.myRetail.exception;

/**
 * Exception class
 * 
 * @author Arunchunai vendan Pugalenthi
 *
 */

public class MyRetailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRetailException(String message) {
		super(message);
	}

	public MyRetailException(String message, Throwable error) {
		super(message, error);
	}

}
