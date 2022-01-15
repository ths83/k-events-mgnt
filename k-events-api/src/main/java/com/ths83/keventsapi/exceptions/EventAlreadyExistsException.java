package com.ths83.keventsapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EventAlreadyExistsException extends RuntimeException {

	private final HttpStatus status;

	private final String message;

	public EventAlreadyExistsException(final String error) {
		super(error);
		message = error;
		status = HttpStatus.BAD_REQUEST;
	}
}
