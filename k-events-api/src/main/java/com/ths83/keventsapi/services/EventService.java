package com.ths83.keventsapi.services;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.model.Event;
import com.ths83.keventsapi.model.EventsPagination;
import com.ths83.keventsapi.repositories.EventRepository;
import com.ths83.keventsapi.utils.EventFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class EventService {

	private final EventRepository repository;

	/**
	 * Format and store the given event to the database.
	 *
	 * @param event The event to save.
	 * @return {@link Event} The saved event.
	 * @throws EventAlreadyExistsException If the exact same event (same fields) is already present, this exception is thrown and the event is not saved.
	 */
	public Event create(@NotNull @Valid final Event event) {

		final var formattedEvent = EventFormatter.format(event);
		final var name = formattedEvent.getName();

		log.info("Saving event '{}'...", name);

		validateEvent(event, name);

		final var response = repository.save(event);
		log.info("Successfully saved event '{}'", name);
		return response;
	}

	private void validateEvent(@NotNull Event event, String name) {
		if (eventAlreadyExists(event, name)) {
			final var error = "Event already exists";
			log.error(error);
			throw new EventAlreadyExistsException(error);
		}
	}

	private boolean eventAlreadyExists(@NotNull Event event, String name) {
		return repository.findEventsByName(name).contains(event);
	}

	/**
	 * Retrieve events by most recent start date, using a pagination to optimize performance and usability.
	 *
	 * @param page The selected page.
	 * @param size The desired number of events to retrieve.
	 * @return {@link EventsPagination}
	 * The desired events, the selected page, the total number of elements, the total number of pages.
	 */
	public EventsPagination getByMostRecentStartDate(final int page, final int size) {
		log.info("Retrieving {} event(s) by most recent date from page {}...", size, page);
		final var events = repository.findAllByOrderByStartDateDesc(PageRequest.of(page, size));
		log.info("Found {} event(s) by most recent date from page {}", events.getNumberOfElements(), page);

		return EventsPagination.builder()
				.currentPage(events.getNumber())
				.totalEvents(events.getTotalElements())
				.totalPages(events.getTotalPages())
				.events(events.getContent()).build();
	}

}
