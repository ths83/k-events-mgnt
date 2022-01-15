package com.ths83.keventsapi.fixtures;

import com.ths83.keventsapi.model.EventsPagination;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EventsResultFixtures {

	public static EventsPagination get() {
		final var events = EventFixtures.getEvents();
		return EventsPagination.builder()
				.events(events)
				.totalEvents(events.size())
				.currentPage(0)
				.totalPages(1)
				.build();
	}
}
