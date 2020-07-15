package com.nec.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDetail {

	@JsonProperty("roomType")
	private String roomType;
	@JsonProperty("roomNumber")
	private String roomNumber;
	@JsonProperty("roomStatus")
	private String roomStatus;
	
	@JsonProperty("roomType")
	public String getRoomType() {
		return roomType;
	}

	@JsonProperty("roomType")
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@JsonProperty("roomNumber")
	public String getRoomNumber() {
		return roomNumber;
	}

	@JsonProperty("roomNumber")
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@JsonProperty("roomStatus")
	public String getRoomStatus() {
		return roomStatus;
	}

	@JsonProperty("roomStatus")
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	
}
