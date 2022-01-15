package com.ths83.keventsapi.configuration.converter;

import com.ths83.keventsapi.fixtures.ZonedDateTimeFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringToZonedDateTimeConverterTest {

	private final ZonedDateTimeToStringConverter converter = new ZonedDateTimeToStringConverter();

	@Test
	void testConvertWithNullDate() {
		// Act
		assertThrows(NullPointerException.class, () -> converter.convert(null));
	}

	@Test
	void testConvertWithSuccess() {
		// Act
		final var date = converter.convert(ZonedDateTimeFixtures.getZonedDateTime());

		// Assert
		assertEquals(ZonedDateTimeFixtures.EUROPE_TIME, date);
	}

}