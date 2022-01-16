package com.ths83.keventsapi.resources;

import com.ths83.keventsapi.exceptions.EventsApiException;
import com.ths83.keventsapi.model.Event;
import com.ths83.keventsapi.model.EventsResult;
import com.ths83.keventsapi.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventResource {

	private static final String UNEXPECTED_EXCEPTION_MSG = "An unexpected exception occurred";

	private final EventService service;

	/**
	 * Create event from given payload.
	 *
	 * @param event The event to be created.
	 * @return The created event.
	 * @throws ResponseStatusException If the exact same event (same fields) exists, a response with status 400 is returned and the event is not created.
	 *                                 <p>
	 *                                 If any other error occurred, a response with status 500 is returned.
	 */
	@PostMapping
	public Event create(@RequestBody final Event event) {
		log.info("Calling POST /events endpoint...");
		try {
			return service.create(event);
		} catch (EventsApiException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (Exception e) {
			log.error(UNEXPECTED_EXCEPTION_MSG);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Retrieve events by most recent start date, using a pagination to optimize performance and usability.
	 *
	 * @param page The selected page.
	 * @param size The desired number of events to retrieve.
	 * @return {@link EventsResult}
	 * The desired events, the selected page, the total number of elements, the total number of pages.
	 * @throws ResponseStatusException If any error occurred, a response with status 500 is returned.
	 */
	@GetMapping
	public EventsResult get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		log.info("Calling GET /events endpoint...");
		try {
			return service.getByMostRecentStartDate(page, size);
		} catch (Exception e) {
			log.error(UNEXPECTED_EXCEPTION_MSG);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
