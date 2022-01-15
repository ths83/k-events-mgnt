package com.ths83.keventsapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Setter
@Getter
@EqualsAndHashCode
@Document("events")
public class Event {

	@NotBlank(message = "Event name must not be blank")
	@Size(min = 1, max = 32, message = "Event name must be between 1 and 32 characters")
	private String name;

	@NotBlank(message = "Event description must not be blank")
	private String description;

	@NotNull(message = "Event startDate cannot be blank")
	private ZonedDateTime startDate;

	@NotNull(message = "Event endDate cannot be blank")
	private ZonedDateTime endDate;

	public Event() {
		super();
	}
}
