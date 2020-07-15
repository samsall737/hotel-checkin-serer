package com.nec.facerecognition.model.response;

import java.util.UUID;

public class FaceRecogniseResponse {

	private String bookingId;
	private UUID faceRecognitionGuestId;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public UUID getFaceRecognitionGuestId() {
		return faceRecognitionGuestId;
	}

	public void setFaceRecognitionGuestId(UUID faceRecognitionGuestId) {
		this.faceRecognitionGuestId = faceRecognitionGuestId;
	}

}
