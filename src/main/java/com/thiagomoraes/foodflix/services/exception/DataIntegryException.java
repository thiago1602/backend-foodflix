package com.thiagomoraes.foodflix.services.exception;

public class DataIntegryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegryException(String msg) {
		super(msg);
		
	}
	
	public DataIntegryException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
