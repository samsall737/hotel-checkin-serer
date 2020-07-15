package com.nec.hotels.model.checkin;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.CardDetails;
import com.nec.hotels.entity.GuestSpecialAmenities;
import com.nec.hotels.model.booking.FlightDetails;

public class CheckinDTO {

	@NotNull(message = "Missing Card details")
	@JsonProperty("card_details")
	private CardDetails cardDetails;
	
	@JsonProperty("face_recognition_guest_id")
	@NotNull(message = "SecondaryGuest Id for face recognition Failed")
	private UUID faceRecognitionGuestId;
	
	@JsonProperty("special_amenities")
	private List<GuestSpecialAmenities> specialAmenities;

	@JsonProperty("special_remarks")
	private String specialRemarks;


	private FlightDetails flightDetails;

	public CardDetails getCreditCard() {
		return cardDetails;
	}

	public void setCreditCard(CardDetails creditCard) {
		this.cardDetails = creditCard;
	}


	public UUID getFaceRecognitionGuestId() {
		return faceRecognitionGuestId;
	}

	public void setFaceRecognitionGuestId(UUID faceRecognitionGuestId) {
		this.faceRecognitionGuestId = faceRecognitionGuestId;
	}

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

	public FlightDetails getFlightDetails() {
		return flightDetails;
	}

	public void setFlightDetails(FlightDetails flightDetails) {
		this.flightDetails = flightDetails;
	}

	

}
