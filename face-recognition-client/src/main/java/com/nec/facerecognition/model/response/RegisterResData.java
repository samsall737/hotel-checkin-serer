package com.nec.facerecognition.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResData {
	private int registrantID;
	private int faceID;

	public int getRegistrantID() {
		return registrantID;
	}

	public void setRegistrantID(int registrantID) {
		this.registrantID = registrantID;
	}

	public int getFaceID() {
		return faceID;
	}

	public void setFaceID(int faceID) {
		this.faceID = faceID;
	}

}
