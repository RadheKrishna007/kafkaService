package com.learnkafka.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.domain.User;
import com.learnkafka.producer.LibraryEventsJsonProducer;
import com.learnkafka.producer.LibraryEventsProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LibraryEventsController {
	
	@Autowired
	private LibraryEventsJsonProducer libraryEventsJsonProducer;
	
	@Autowired
	private LibraryEventsProducer libraryEventProducer;
	
	

	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvents(@RequestBody LibraryEvent libraryEvents) throws JsonProcessingException, InterruptedException, ExecutionException{
	//	libraryEventProducer.sendLibraryEvent(libraryEvents);
	//	libraryEventProducer.sendLibraryEventApproach2(libraryEvents);
		libraryEventProducer.sendLibraryEventApproach3(libraryEvents);
		log.info("After Sending Event ");
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvents);
	}
	
	@PutMapping("/v1/libraryevent")
	public ResponseEntity<?> putLibraryEvents(@RequestBody LibraryEvent libraryEvents) throws JsonProcessingException, InterruptedException, ExecutionException{
	//	libraryEventProducer.sendLibraryEvent(libraryEvents);
	//	libraryEventProducer.sendLibraryEventApproach2(libraryEvents);
		
		if(libraryEvents.libraryEventId==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Libray event id cannot be null");
		}
		
		if(!libraryEvents.getLibraryEventType().equalsIgnoreCase("UPDATE")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Libray event type should be update");
		}
		
		libraryEventProducer.sendLibraryEventApproach3(libraryEvents);
		log.info("After Sending Event ");
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvents);
	}
	
	@PostMapping("/v1/jsonEvent")
	public ResponseEntity<String> publish(@RequestBody User user){
		
		libraryEventsJsonProducer.sendMessage(user);
		
		return ResponseEntity.ok("Message sent successfully");
		
	}
	
}
