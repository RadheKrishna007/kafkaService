package com.learnkafka.domain;

public class LibraryEvent {
	
	public Integer libraryEventId;
	public String libraryEventType;
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
	
	@Override
	public String toString() {
		return "LibraryEvent [libraryEventId=" + libraryEventId + ", libraryEventType=" + libraryEventType + ", book="
				+ book + "]";
	}
	
	

}
