package com.learnkafka.jpa;

import org.springframework.data.repository.CrudRepository;

import com.learnkafka.domain.LibraryEvent;

public interface LibraryEventsRepository extends CrudRepository<LibraryEvent, Integer> {

}
