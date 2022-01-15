package com.ths83.keventsapi.model;

import com.ths83.keventsapi.fixtures.EventFixtures;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventValidationTest {

	private static final String BLANK_NAME_MSG = "Event name must not be blank";
	private static final String TOO_LONG_NAME_MSG = "Event name must be between 1 and 32 characters";
	private static final String BLANK_DESC_MSG = "Event description must not be blank";
	private static final String BLANK_START_DT_MSG = "Event startDate cannot be blank";
	private static final String BLANK_END_DT_MSG = "Event endDate cannot be blank";

	private final Validator validator =
			Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	public void testEmptyEvent() {
		// Act
		final var event = new Event();
		final var violations = validator.validate(event);

		// Assert
		assertFalse(violations.isEmpty());
		assertEquals(4, violations.size());
		violations.forEach(v -> assertTrue(getBlankErrorMessages().contains(v.getMessage())));
	}

	@Test
	public void testNoNameEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.getWithNoName());

		// Assert
		assertEqualsHelper(violations, BLANK_NAME_MSG);
	}

	@Test
	public void testNoDescEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.getWithNoDescription());

		// Assert
		assertEqualsHelper(violations, BLANK_DESC_MSG);
	}

	@Test
	public void testNoStartDateEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.getWithNoStartDate());

		// Assert
		assertEqualsHelper(violations, BLANK_START_DT_MSG);
	}

	@Test
	public void testNoEndDateEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.getWithNoEndDate());

		// Assert
		assertEqualsHelper(violations, BLANK_END_DT_MSG);
	}

	@Test
	public void testTooLongNameEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.getWithTooLongName());

		// Assert
		assertEqualsHelper(violations, TOO_LONG_NAME_MSG);
	}

	@Test
	public void testValidEvent() {
		// Act
		final var violations = validator.validate(EventFixtures.get());

		// Assert
		assertTrue(violations.isEmpty());
	}

	private void assertEqualsHelper(final Set<ConstraintViolation<Event>> violations, final String errorMsg) {
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		violations.forEach(v -> assertEquals(errorMsg, v.getMessage()));
	}

	private List<String> getBlankErrorMessages() {
		return List.of(BLANK_NAME_MSG, BLANK_DESC_MSG, BLANK_START_DT_MSG, BLANK_END_DT_MSG);
	}

}