package com.ths83.keventsapi.services;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.fixtures.EventFixtures;
import com.ths83.keventsapi.model.Event;
import com.ths83.keventsapi.repositories.EventRepository;
import com.ths83.keventsapi.utils.EventFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

	public static final String NEW_NAME = "NEW";

	@Mock
	private EventRepository repository;

	@InjectMocks
	private EventService service;

	// create with null
	@Test
	void testWithNullEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(null));

		// Assert
		verifyNoInteractions(repository);
	}

	// create with empty
	@Test
	void testWithEmptyEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(new Event()));

		// Assert
		verifyNoInteractions(repository);
	}

	// create with invalid
	@Test
	void testWithInvalidEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(EventFixtures.getWithNoName()));
		Assertions.assertThrows(NullPointerException.class, () -> service.create(EventFixtures.getWithNoDescription()));

		// Assert
		verifyNoInteractions(repository);
	}

	// create with non formatted event
	@Test
	void testWithNonFormattedEvent() {
		// Arrange
		final var event = EventFixtures.getToTrim();
		final var formattedEvent = EventFormatter.format(event);

		when(repository.findEventsByName(any())).thenReturn(List.of());
		when(repository.save(any())).thenReturn(formattedEvent);

		// Act
		final var response = service.create(event);

		// Assert
		assertEquals(formattedEvent.getName(), response.getName());
		assertEquals(formattedEvent.getDescription(), response.getDescription());
		assertEquals(formattedEvent.getStartDate(), response.getStartDate());
		assertEquals(formattedEvent.getEndDate(), response.getEndDate());

		verify(repository).findEventsByName(any());
		verify(repository).save(any());
	}

	// create with formatted event
	@Test
	void testWithFormattedEvent() {
		// Arrange
		final var formattedEvent = EventFormatter.format(EventFixtures.get());

		when(repository.findEventsByName(any())).thenReturn(List.of());
		when(repository.save(any())).thenReturn(formattedEvent);

		// Act
		final var response = service.create(formattedEvent);

		// Assert
		assertSame(response, formattedEvent);

		verify(repository).findEventsByName(any());
		verify(repository).save(any());
	}

	// create with existing event in db
	@Test
	void testWithDuplicatedEvent() {
		// Arrange
		final var event = EventFixtures.get();
		when(repository.findEventsByName(any())).thenReturn(List.of(event));

		// Act
		assertThrows(EventAlreadyExistsException.class, () -> service.create(event));

		// Assert
		verify(repository).findEventsByName(any());
		verify(repository, never()).save(any());
	}

	// create with non-existing event in db
	@Test
	void testWithNewEvent() {
		// Arrange
		final var event = EventFixtures.get();
		event.setName(NEW_NAME);
		when(repository.findEventsByName(any())).thenReturn(List.of(EventFixtures.get()));
		when(repository.save(any())).thenReturn(event);

		// Act
		final var response = service.create(event);

		// Assert
		assertSame(response, event);

		verify(repository).findEventsByName(any());
		verify(repository).save(any());
	}

}