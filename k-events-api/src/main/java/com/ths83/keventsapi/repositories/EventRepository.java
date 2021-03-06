package com.ths83.keventsapi.repositories;

import com.ths83.keventsapi.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

	List<Event> findEventsByName(final String name);

	Page<Event> findAllByOrderByStartDateDesc(Pageable pageable);
}
