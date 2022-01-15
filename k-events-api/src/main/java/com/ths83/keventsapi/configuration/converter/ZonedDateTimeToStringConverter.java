package com.ths83.keventsapi.configuration.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;

public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

	/**
	 * MongoDB does not support ZonedDateTime. It must be converted to a String.
	 *
	 * @param source The date to convert.
	 * @return The string value of the given date.
	 */
	@Override
	public String convert(ZonedDateTime source) {
		return source.toString();
	}
}