package com.ths83.keventsapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class EventsApiException extends RuntimeException {

	private final HttpStatus status;

	private final String message;

	public EventsApiException(final HttpStatus status1, final String message1) {
		super(message1);
		message = message1;
		status = status1;
	}
}
