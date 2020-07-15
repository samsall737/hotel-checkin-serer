package com.nec.hotels.model.booking;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MultipleBookings {

	private String hotelCode;

	private List<String> confirmationNumbers;

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public List<String> getConfirmationNumbers() {
		return confirmationNumbers;
	}

	@JsonProperty("confirmationNumbers")
	public void setConfirmationNumbers(List<String> confirmationNumbers) {
		this.confirmationNumbers = confirmationNumbers;
	}
}
