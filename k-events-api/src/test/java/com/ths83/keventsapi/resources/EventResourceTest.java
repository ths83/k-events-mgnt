package com.ths83.keventsapi.resources;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.fixtures.EventFixtures;
import com.ths83.keventsapi.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
	void testGetWithException() {
		// Arrange
		when(service.get()).thenThrow(RuntimeException.class);

		// Act
		final var exception = assertThrows(ResponseStatusException.class, () -> resource.get());

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		assertNull(exception.getReason());
		verify(service).get();
	}

	@Test
	void testGetWithSuccess() {
		// Arrange
		when(service.get()).thenReturn(List.of(EventFixtures.get(), EventFixtures.getWithNoName()));

		// Act
		final var events = resource.get();

		// Assert
		assertEquals(2, events.size());
		assertTrue(events.contains(EventFixtures.get()));
		assertTrue(events.contains(EventFixtures.getWithNoName()));
		verify(service).get();
	}

}