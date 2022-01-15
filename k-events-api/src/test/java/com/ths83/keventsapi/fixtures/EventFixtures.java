package com.ths83.keventsapi.fixtures;

import com.ths83.keventsapi.model.Event;
import org.apache.commons.lang3.RandomStringUtils;

public class EventFixtures {

	private static final String DESCRIPTION = "desc";
	private static final String START_DATE = "start";
	private static final String END_DATE = "end";
	private static final String NAME = "name";
	private static final String NAME_WITH_SPACES = "    name      ";
	private static final String DESCRIPTION_WITH_SPACES = "  desc    ";
	private static final int TOO_LONG_NAME = 32;

	public static Event getWithNoName() {
		final var event = get();
		event.setName(null);
		return event;
	}

	public static Event getWithNoDescription() {
		final var event = get();
		event.setDescription(null);
		return event;
	}

	public static Event getWithNoStartDate() {
		final var event = get();
		event.setStartDate(null);
		return event;
	}

	public static Event getWithNoEndDate() {
		final var event = get();
		event.setEndDate(null);
		return event;
	}

	public static Event getToTrim() {
		final var event = get();
		event.setName(NAME_WITH_SPACES);
		event.setDescription(DESCRIPTION_WITH_SPACES);
		return event;
	}

	public static Event getWithTooLongName() {
		final var event = get();
		event.setName(RandomStringUtils.randomAlphabetic(TOO_LONG_NAME + 1));
		return event;
	}

	public static Event get() {
		final var event = new Event();
		event.setName(NAME);
		event.setDescription(DESCRIPTION);
		event.setStartDate(START_DATE);
		event.setEndDate(END_DATE);

		return event;
	}
}
