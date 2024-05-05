package com.learnkafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsJsonProducer {

	@Autowired
	private KafkaTemplate<Integer, LibraryEvent> kafkaTemplate;
	
	public void sendMessage(User user) {
		
		Message<User> mess = MessageBuilder
				.withPayload(user)
				.setHeader(KafkaHeaders.TOPIC, "json-events")
				.build();
		kafkaTemplate.send(mess);
		log.info("Message sent successfully  : {} " + user);
		
	}
	
	

}
