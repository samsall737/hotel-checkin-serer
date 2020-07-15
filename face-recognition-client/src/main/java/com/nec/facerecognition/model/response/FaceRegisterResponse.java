package com.nec.facerecognition.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceRegisterResponse {

	private RegisterResHeader resHeader;
	private RegisterResData resData;

	public RegisterResHeader getResHeader() {
		return resHeader;
	}

	public void setResHeader(RegisterResHeader resHeader) {
		this.resHeader = resHeader;
	}

	public RegisterResData getResData() {
		return resData;
	}

	public void setResData(RegisterResData resData) {
		this.resData = resData;
	}

}
