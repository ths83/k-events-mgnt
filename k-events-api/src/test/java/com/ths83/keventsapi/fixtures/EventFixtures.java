package com.ths83.keventsapi.fixtures;

import com.ths83.keventsapi.model.Event;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EventFixtures {

	private static final String DESCRIPTION = "desc";
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
		event.setStartDate(ZonedDateTimeFixtures.getZonedDateTime());
		event.setEndDate(ZonedDateTimeFixtures.getZonedDateTime());

		return event;
	}

	public static Page<Event> getPageEvents() {
		return new PageImpl<>(EventFixtures.getEvents());
	}

	public static Page<Event> getEmptyPageEvents() {
		return new PageImpl<>(List.of());
	}

	static List<Event> getEvents() {
		return List.of(get(), getWithNoName());
	}
}
