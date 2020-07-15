package com.nec.hotels.model.booking;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;

//@Entity
//@Table(name = "room")
@JsonInclude(Include.NON_NULL)
public class Room {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	private UUID id;

	@Column(name = "room_number")
//	@NotNull
	@JsonProperty("room_number")
	private Integer roomNumber;

	@JsonProperty("room_status")
	private String roomStatus;
	
	@Column(name = "type")
	@NotBlank
	private String type;

	@Column(name = "rate")
	@JsonProperty("room_rate")
	private long rate;

	public Room(Integer roomNumber, String type, long rate) {
		super();
		this.roomNumber = roomNumber;
		this.type = type;
		this.rate = rate;
	}
	
	public Room(Integer roomNumber, String type, String roomStatus , long rate) {
		super();
		this.roomNumber = roomNumber;
		this.type = type;
		this.roomStatus = roomStatus;
		this.rate = rate;
	}


	public Room() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	

}
