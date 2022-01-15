package com.ths83.keventsapi.utils;

import com.ths83.keventsapi.fixtures.EventFixtures;
import com.ths83.keventsapi.model.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventFormatterTest {

	@Test
	void testFormatWithNullEvent_NPE() {
		// Act
		assertThrows(NullPointerException.class, () -> EventFormatter.format(null));
	}

	@Test
	void testFormatWithEmptyEvent_NPE() {
		// Act
		assertThrows(NullPointerException.class, () -> EventFormatter.format(new Event()));
	}

	@Test
	void testFormatWithNoNameEvent_NPE() {
		// Act
		assertThrows(NullPointerException.class, () -> EventFormatter.format(EventFixtures.getWithNoName()));
	}

	@Test
	void testFormatWithNoDescriptionEvent_NPE() {
		// Act
		assertThrows(NullPointerException.class, () -> EventFormatter.format(EventFixtures.getWithNoDescription()));
	}

	@Test
	void testFormatWithSpaces() {
		// Act
		final var expected = EventFixtures.get();
		final var formattedEvent = EventFormatter.format(EventFixtures.getToTrim());

		// Assert
		assertEventHelper(expected, formattedEvent);
	}

	@Test
	void testFormat() {
		// Act
		final var event = EventFixtures.get();
		final var formattedEvent = EventFormatter.format(event);

		// Assert
		assertEventHelper(event, formattedEvent);
	}

	private void assertEventHelper(Event expected, Event formattedEvent) {
		assertEquals(expected.getName(), formattedEvent.getName());
		assertEquals(expected.getDescription(), formattedEvent.getDescription());
		assertEquals(expected.getStartDate(), formattedEvent.getStartDate());
		assertEquals(expected.getEndDate(), formattedEvent.getEndDate());
	}

}