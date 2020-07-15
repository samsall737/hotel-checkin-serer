package com.nec.master.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DBConnection {

	private UUID id;

	private String url;

	@JsonProperty("user_name")
	private String userName;

	private String password;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}