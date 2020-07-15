package com.nec.master.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private UUID id;

	private String name;

	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("password")
	private String password;

	@JsonProperty("email")
	private String email;
	
	private int type;
	
	@JsonProperty("hotel_id")
	private UUID hotelId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UUID getHotelId() {
		return hotelId;
	}

	public void setHotelId(UUID hotelId) {
		this.hotelId = hotelId;
	}

}
