package com.ths83.keventsapi.fixtures;

import com.ths83.keventsapi.model.EventsResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EventsResultFixtures {

	public static EventsResult get() {
		final var events = EventFixtures.getEvents();
		return EventsResult.builder()
				.events(events)
				.totalEvents(events.size())
				.currentPage(0)
				.totalPages(1)
				.build();
	}
}
