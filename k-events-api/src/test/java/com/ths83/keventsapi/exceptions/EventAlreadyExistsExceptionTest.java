package com.ths83.keventsapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventAlreadyExistsExceptionTest {

	private static final String ERROR_MSG = "The event already exists";

	@Test
	void testConstructor() {
		// Act
		final var exception = new EventAlreadyExistsException();

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		assertEquals(ERROR_MSG, exception.getMessage());
	}

}