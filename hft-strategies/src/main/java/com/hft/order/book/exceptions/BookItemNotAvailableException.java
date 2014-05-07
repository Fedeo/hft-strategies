package com.hft.order.book.exceptions;

public class BookItemNotAvailableException extends Exception {

	private static final long serialVersionUID = -5342592206349398809L;

	private String description;

	public BookItemNotAvailableException(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
