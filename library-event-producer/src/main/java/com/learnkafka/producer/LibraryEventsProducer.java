package com.learnkafka.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsProducer {

	@Value("${spring.kafka.topic}")
	public String topic;

	private final KafkaTemplate<Integer, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public LibraryEventsProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public CompletableFuture<SendResult<Integer, String>> sendLibraryEvent(LibraryEvent libraryEvent)
			throws JsonProcessingException {
		var value = objectMapper.writeValueAsString(libraryEvent);
		var key = libraryEvent.getLibraryEventId();

		// 1. blocking call - gets metadata about the kafka cluster
		// 2. send message happends - returns a completablefuture (Asyncronous call)
		// CompletableFuture<SendResult<Integer,String>> completableFuture =
		// (CompletableFuture<SendResult<Integer, String>>) kafkaTemplate.send(topic,
		// key, value);
		ListenableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(topic, key, value);

		return completableFuture.completable().whenComplete((sendResult, throwable) -> {
			if (throwable != null) {
				handleFailure(key, value, throwable);
			} else {
				handleSuccess(key, value, sendResult);
			}
		});
	}

	public SendResult<Integer, String> sendLibraryEventApproach2(LibraryEvent libraryEvent)
			throws JsonProcessingException, InterruptedException, ExecutionException {
		var value = objectMapper.writeValueAsString(libraryEvent);
		var key = libraryEvent.getLibraryEventId();

		// 1. blocking call - gets metadata about the kafka cluster
		// 2. block and wait until message send to kafka syncronous calls
		SendResult<Integer, String> completableFuture = kafkaTemplate.send(topic, key, value).get();
		handleSuccess(key, value, completableFuture);
		return completableFuture;
	}

	public CompletableFuture<SendResult<Integer, String>> sendLibraryEventApproach3(LibraryEvent libraryEvent)
			throws JsonProcessingException {
		var value = objectMapper.writeValueAsString(libraryEvent);
		var key = libraryEvent.getLibraryEventId();
		var producerRecord = buildProducerRecord(key, value);
		// 1. blocking call - gets metadata about the kafka cluster
		// 2. send message happends - returns a completablefuture (Asyncronous call)
		// CompletableFuture<SendResult<Integer,String>> completableFuture =
		// (CompletableFuture<SendResult<Integer, String>>) kafkaTemplate.send(topic,
		// key, value);
		ListenableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(producerRecord);

		return completableFuture.completable().whenComplete((sendResult, throwable) -> {
			if (throwable != null) {
				handleFailure(key, value, throwable);
			} else {
				handleSuccess(key, value, sendResult);
			}
		});
	}

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value) {
	//	Header headers = Header
		
		List<Header> header = List.of(new RecordHeader("event-source","scanner".getBytes()));
		
		return new ProducerRecord<>(topic, null, key, value, header);

	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
		log.info("Message sent successfully for the key : {} and the value : {} , partition is {} ", key, value,
				sendResult.getRecordMetadata().partition());

	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error sending the message and the exception is {} ", ex.getMessage(), ex);

	}

}
