package com.learnkafka.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Builder
public class LibraryEvent {
	@Id
	@GeneratedValue
	public Integer libraryEventId;
	
	public String libraryEventType;
	
	@OneToOne(mappedBy = "libraryEvent", cascade = {CascadeType.ALL})
	@ToString.Exclude
	public Book book;
	
	
	
	public Integer getLibraryEventId() {
		return libraryEventId;
	}
	public void setLibraryEventId(Integer libraryEventId) {
		this.libraryEventId = libraryEventId;
	}
	public String getLibraryEventType() {
		return libraryEventType;
	}
	public void setLibraryEventType(String libraryEventType) {
		this.libraryEventType = libraryEventType;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	
	
	public LibraryEvent(Integer libraryEventId, String libraryEventType, Book book) {
		super();
		this.libraryEventId = libraryEventId;
		this.libraryEventType = libraryEventType;
		this.book = book;
	}
	public LibraryEvent() {

	}
	
}
