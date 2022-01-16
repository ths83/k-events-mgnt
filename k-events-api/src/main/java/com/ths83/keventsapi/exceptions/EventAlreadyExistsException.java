package com.ths83.keventsapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EventAlreadyExistsException extends EventsApiException {

	private static final String ALREADY_EXISTS_ERROR = "The event already exists";

	public EventAlreadyExistsException() {
		super(HttpStatus.BAD_REQUEST, ALREADY_EXISTS_ERROR);
	}
}
