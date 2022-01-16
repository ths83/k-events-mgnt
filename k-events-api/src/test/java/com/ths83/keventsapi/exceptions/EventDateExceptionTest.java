package com.ths83.keventsapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDateExceptionTest {

	private static final String ERROR_MSG = "The start date is greater than the end date";

	@Test
	void testConstructor() {
		// Act
		final var exception = new EventDateException();

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		assertEquals(ERROR_MSG, exception.getMessage());
	}

}