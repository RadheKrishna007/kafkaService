package com.learnkafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventJsonConsumer {
	
	@KafkaListener(topics = {"json-events"})
	public void consumer(User user) {
		log.info("message received -> {}", user);
	}

}
