package com.nec.hotels.model.checkin;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckinDetails {

	private String id;

	@NotNull(message = "Missing check-in time")
	@JsonProperty("check_in_time")
	private long checkInTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(long checkInTime) {
		this.checkInTime = checkInTime;
	}
}
