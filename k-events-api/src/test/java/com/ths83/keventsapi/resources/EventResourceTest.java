package com.ths83.keventsapi.resources;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.fixtures.EventFixtures;
import com.ths83.keventsapi.fixtures.EventsResultFixtures;
import com.ths83.keventsapi.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventResourceTest {

	private static final String ERROR_MSG = "error";

	@Mock
	private EventService service;

	@InjectMocks
	private EventResource resource;

	@Test
	void testCreateWithSuccess() {
		// Arrange
		final var expected = EventFixtures.get();
		when(service.create(any())).thenReturn(expected);

		// Act
		final var event = resource.create(EventFixtures.get());

		// Assert
		assertSame(expected, event);
		verify(service).create(any());
	}

	@Test
	void testCreateWithDuplicatedEvent() {
		// Arrange
		when(service.create(any())).thenThrow(new EventAlreadyExistsException(ERROR_MSG));

		// Act
		final var exception = assertThrows(ResponseStatusException.class, () -> resource.create(EventFixtures.get()));

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		assertEquals(ERROR_MSG, exception.getReason());
		verify(service).create(any());
	}

	@Test
	void testCreateWithOtherException() {
		// Arrange
		when(service.create(any())).thenThrow(RuntimeException.class);

		// Act
		final var exception = assertThrows(ResponseStatusException.class, () -> resource.create(EventFixtures.get()));

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		assertNull(exception.getReason());
		verify(service).create(any());
	}

	@Test
	void testGetByMostRecentStartDateWithException() {
		// Arrange
		when(service.getByMostRecentStartDate(anyInt(), anyInt())).thenThrow(RuntimeException.class);

		// Act
		final var exception = assertThrows(ResponseStatusException.class, () -> resource.get(0, 0));

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		assertNull(exception.getReason());
		verify(service).getByMostRecentStartDate(0, 0);
	}

	@Test
	void testGetByMostRecentStartDateWithSuccess() {
		// Arrange
		when(service.getByMostRecentStartDate(anyInt(), anyInt())).thenReturn(EventsResultFixtures.get());

		// Act
		final var eventsResult = resource.get(0, 5);

		// Assert
		assertEquals(2, eventsResult.getEvents().size());
		assertTrue(eventsResult.getEvents().contains(EventFixtures.get()));
		assertTrue(eventsResult.getEvents().contains(EventFixtures.getWithNoName()));
		assertEquals(0, eventsResult.getCurrentPage());
		assertEquals(1, eventsResult.getTotalPages());
		assertEquals(2, eventsResult.getTotalEvents());

		verify(service).getByMostRecentStartDate(0, 5);
	}

}