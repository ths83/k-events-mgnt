package com.ths83.keventsapi.configuration.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;

public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

	/**
	 * MongoDB does not support ZonedDateTime. It must be converted to a String, then back to the original class.
	 *
	 * @param source The date to convert.
	 * @return The ZonedDateTime object of the given date.
	 */
	@Override
	public ZonedDateTime convert(String source) {
		return ZonedDateTime.parse(source);
	}

}