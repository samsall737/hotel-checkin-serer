package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckInComplete {

	@JsonProperty("attributes")
	private Attributes attributes;
	
	@JsonProperty("reservationID")
	private ReservationID reservationID;
	
	@JsonProperty("room")
	private Room room;
	
	@JsonProperty("checkOutTime")
	private String checkOutTime;
	
	@JsonProperty("attributes")
	public Attributes getAttributes() {
		return attributes;
	}
	
	@JsonProperty("attributes")
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
	@JsonProperty("reservationID")
	public ReservationID getReservationID() {
		return reservationID;
	}
	
	@JsonProperty("reservationID")
	public void setReservationID(ReservationID reservationID) {
		this.reservationID = reservationID;
	}
	
	@JsonProperty("room")
	public Room getRoom() {
		return room;
	}
	
	@JsonProperty("room")
	public void setRoom(Room room) {
		this.room = room;
	}
	
	@JsonProperty("checkOutTime")
	public String getCheckOutTime() {
		return checkOutTime;
	}
	
	@JsonProperty("checkOutTime")
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

}