package com.ths83.keventsapi.utils;

import com.ths83.keventsapi.model.Event;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EventFormatter {

	/**
	 * Format event by trimming name and description.
	 *
	 * @param event The event to format.
	 * @return The formatted event.
	 */
	public static Event format(@NotNull final Event event) {
		event.setName(event.getName().trim());
		event.setDescription(event.getDescription().trim());
		return event;
	}
}
