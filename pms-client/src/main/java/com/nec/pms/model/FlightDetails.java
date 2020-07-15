package com.nec.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetails {

	@JsonProperty("arrivalTransport")
	private Transport arrivalTransport;

	private Transport departureTransport;

	public Transport getDepartureTransport() {
		return departureTransport;
	}

	public void setDepartureTransport(Transport departureTransport) {
		this.departureTransport = departureTransport;
	}

	@JsonProperty("arrivalTransport")
	public Transport getArrivalTransport() {
		return arrivalTransport;
	}

	@JsonProperty("arrivalTransport")
	public void setArrivalTransport(Transport arrivalTransport) {
		this.arrivalTransport = arrivalTransport;
	}

}
