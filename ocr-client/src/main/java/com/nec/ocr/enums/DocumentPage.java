package com.nec.ocr.enums;

public enum DocumentPage {


	FRONT("front"), BACK("back");

	private String type;

	private DocumentPage(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
