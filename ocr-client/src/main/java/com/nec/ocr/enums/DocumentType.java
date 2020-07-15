package com.nec.ocr.enums;

 public enum DocumentType {
	
	PASSPORT("passport"), AADHAR_CARD("aadhaar"), DRIVING_LICENCE("driving licence"), VISA("visa");

	private String type;

	private DocumentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	

}
