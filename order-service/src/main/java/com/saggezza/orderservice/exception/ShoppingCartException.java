package com.saggezza.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ShoppingCartException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShoppingCartException() {
		super();
	}

	public ShoppingCartException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ShoppingCartException(final String message) {
		super(message);
	}

	public ShoppingCartException(final Throwable cause) {
		super(cause);
	}

}