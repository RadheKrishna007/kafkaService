package com.learnkafka.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	private Integer id;
	private String name;
	private String age;
	
	public User() {
	}
	
	public User(Integer id, String name, String age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	
	
	
	
}
