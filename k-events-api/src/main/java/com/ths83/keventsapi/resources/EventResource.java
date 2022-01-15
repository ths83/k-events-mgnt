package com.ths83.keventsapi.resources;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.model.Event;
import com.ths83.keventsapi.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventResource {

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
		} catch (EventAlreadyExistsException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (Exception e) {
			log.error("An unexpected exception occurred");
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Retrieve all events.
	 *
	 * @return All available events, or an empty list.
	 * @throws ResponseStatusException If any error occurred, a response with status 500 is returned.
	 */
	@GetMapping
	public List<Event> get() {
		log.info("Calling GET /events endpoint...");
		try {
			return service.get();
		} catch (Exception e) {
			log.error("An unexpected exception occurred");
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
