package com.nec.hotels.model.booking;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "flight_details")
@JsonInclude(Include.NON_NULL)
public class FlightDetails {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	private UUID id;

	@Column(name = "number")
    private String number;
    
	@Transient
    @JsonProperty("arrival_time")
    private String arrivalTime;
    
	@Transient
    @JsonProperty("departure_time")
    private String departureTime;
    
	@Transient
    @JsonProperty("arrival_transportation_required")
    private boolean arrivalTransportationRequired;
    
	@Transient
    @JsonProperty("departure_transportation_required")
    private boolean departureTransportationRequired;
    
	@Column(name = "date")
    @JsonProperty("date")
	private Long date;
	

    public FlightDetails() {}

	public FlightDetails(String number, String arrivalTime, String departureTime, boolean arrivalTransportationRequired,
			boolean departureTransportationRequired) {
		super();
		this.number = number;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.arrivalTransportationRequired = arrivalTransportationRequired;
		this.departureTransportationRequired = departureTransportationRequired;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public boolean isArrivalTransportationRequired() {
		return arrivalTransportationRequired;
	}

	public void setArrivalTransportationRequired(boolean arrivalTransportationRequired) {
		this.arrivalTransportationRequired = arrivalTransportationRequired;
	}

	public boolean isDepartureTransportationRequired() {
		return departureTransportationRequired;
	}

	public void setDepartureTransportationRequired(boolean departureTransportationRequired) {
		this.departureTransportationRequired = departureTransportationRequired;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

    
}
