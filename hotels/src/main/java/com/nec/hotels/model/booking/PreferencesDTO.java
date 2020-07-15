package com.nec.hotels.model.booking;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.GuestSpecialAmenities;

public class PreferencesDTO {

	@JsonProperty("special_amenities")
	private List<GuestSpecialAmenities> specialAmenities;

	@JsonProperty("special_remarks")
	private String specialRemarks;

	@JsonProperty("flight_details")
	private FlightDetails flightDetailsDTO;

	public List<GuestSpecialAmenities> getSpecialAmenities() {
		return specialAmenities;
	}

	public void setSpecialAmenities(List<GuestSpecialAmenities> specialAmenities) {
		this.specialAmenities = specialAmenities;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public FlightDetails getFlightDetailsDTO() {
		return flightDetailsDTO;
	}

	public void setFlightDetailsDTO(FlightDetails flightDetailsDTO) {
		this.flightDetailsDTO = flightDetailsDTO;
	}
}