package com.nec.hotels.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QrCode {

	@JsonProperty("qr_code")
	private String qrCode;

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	public String getQrCode() {
		return this.qrCode;
	}

}
