package com.ths83.keventsapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EventDateException extends EventsApiException {

	private static final String DATE_ERROR_MSG = "The start date is greater than the end date";

	public EventDateException() {
		super(HttpStatus.BAD_REQUEST, DATE_ERROR_MSG);
	}
}
