package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Room {
	
	@JsonProperty("roomNumber")
	private RoomNumber roomNumber;
	
	@JsonProperty("roomDescription")
	private RoomDescription roomDescription;
	
	@JsonProperty("roomType")
	private RoomType roomType;
	
	@JsonProperty("roomNumber")
	public RoomNumber getRoomNumber() {
		return roomNumber;
	}
	
	@JsonProperty("roomNumber")
	public void setRoomNumber(RoomNumber roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	@JsonProperty("roomDescription")
	public RoomDescription getRoomDescription() {
		return roomDescription;
	}
	
	@JsonProperty("roomDescription")
	public void setRoomDescription(RoomDescription roomDescription) {
		this.roomDescription = roomDescription;
	}
	
	@JsonProperty("roomType")
	public RoomType getRoomType() {
		return roomType;
	}
	
	@JsonProperty("roomType")
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

}