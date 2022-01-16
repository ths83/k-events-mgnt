package com.ths83.keventsapi.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EventsResult {

	private List<Event> events;

	private int currentPage;

	private long totalEvents;

	private int totalPages;
}
