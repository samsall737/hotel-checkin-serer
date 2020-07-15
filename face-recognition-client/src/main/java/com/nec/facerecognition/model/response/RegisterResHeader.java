package com.nec.facerecognition.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResHeader {
	private String resDatetime;
	private int errorCode;
	private String errorMsg;

	public String getResDatetime() {
		return resDatetime;
	}

	public void setResDatetime(String resDatetime) {
		this.resDatetime = resDatetime;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
