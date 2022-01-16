package com.ths83.keventsapi.services;

import com.ths83.keventsapi.exceptions.EventAlreadyExistsException;
import com.ths83.keventsapi.exceptions.EventDateException;
import com.ths83.keventsapi.fixtures.EventFixtures;
import com.ths83.keventsapi.model.Event;
import com.ths83.keventsapi.model.EventsResult;
import com.ths83.keventsapi.repositories.EventRepository;
import com.ths83.keventsapi.utils.EventFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
	private static final String SIZE_ERROR_MSG = "Page size must not be less than one!";

	@Mock
	private EventRepository repository;

	@InjectMocks
	private EventService service;

	@Test
	void testWithNullEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(null));

		// Assert
		verifyNoInteractions(repository);
	}

	@Test
	void testWithEmptyEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(new Event()));

		// Assert
		verifyNoInteractions(repository);
	}

	@Test
	void testWithInvalidEvent() {
		// Act
		Assertions.assertThrows(NullPointerException.class, () -> service.create(EventFixtures.getWithNoName()));
		Assertions.assertThrows(NullPointerException.class, () -> service.create(EventFixtures.getWithNoDescription()));

		// Assert
		verifyNoInteractions(repository);
	}

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

	@Test
	void testWithDateErrorEvent() {
		// Arrange
		final var event = EventFixtures.getWithGreaterStartDate();
		when(repository.findEventsByName(any())).thenReturn(List.of(EventFixtures.get()));

		// Act
		assertThrows(EventDateException.class, () -> service.create(event));

		// Assert
		verify(repository).findEventsByName(any());
		verify(repository, never()).save(any());
	}

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

	@Test
	void testGetByMostRecentStartDateWithNoPage() {
		// Act
		final var exception = assertThrows(IllegalArgumentException.class, () -> service.getByMostRecentStartDate(0, 0));

		// Assert
		assertEquals(SIZE_ERROR_MSG, exception.getMessage());
	}

	@Test
	void testGetByMostRecentStartDate() {
		// Arrange
		final var pageEvents = EventFixtures.getPageEvents();
		when(repository.findAllByOrderByStartDateDesc(PageRequest.of(0, 2))).thenReturn(pageEvents);

		// Act
		final var result = service.getByMostRecentStartDate(0, 2);

		// Assert
		assertGetHelper(result, pageEvents);
	}

	@Test
	void testGetByMostRecentStartDateWithMoreThanTwoElements() {
		// Arrange
		final var pageEvents = EventFixtures.getPageEvents();
		when(repository.findAllByOrderByStartDateDesc(PageRequest.of(0, 3))).thenReturn(pageEvents);

		// Act
		final var result = service.getByMostRecentStartDate(0, 3);

		// Assert
		assertGetHelper(result, pageEvents);
	}

	private void assertGetHelper(final EventsResult result, final Page<Event> pageEvents) {
		assertEquals(2, result.getEvents().size());
		result.getEvents().forEach(ev -> pageEvents.getContent().contains(ev));
		assertEquals(pageEvents.getTotalElements(), result.getTotalEvents());
		assertEquals(pageEvents.getTotalPages(), result.getTotalPages());
		assertEquals(pageEvents.getNumber(), result.getCurrentPage());
	}

	@Test
	void testGetByMostRecentStartDateWithoutResult() {
		// Arrange
		when(repository.findAllByOrderByStartDateDesc(PageRequest.of(1, 3)))
				.thenReturn(EventFixtures.getEmptyPageEvents());

		// Act
		final var result = service.getByMostRecentStartDate(1, 3);

		// Assert
		assertEquals(0, result.getEvents().size());
	}

}