package com.nec.hotels.enums;

import java.util.HashMap;
import java.util.Map;

public enum ValidExtensions {
	JPG(".jpg"), JPEG(".jpeg"), PNG(".png"), PDF(".pdf");

	private String description;
	private static final Map<String, ValidExtensions> types = new HashMap<>();

	private ValidExtensions(String description) {
			this.description = description;
		}

	public String getDescription() {
		return description;
	}
	
	static {
		for (final ValidExtensions validExtensions : ValidExtensions.values()) {
			types.put(validExtensions.getDescription(), validExtensions);
		}
	}
	
	public static boolean contains(String type) {
		return types.containsKey(type.toLowerCase());
	}

	@Override
	public String toString() {
		switch (this) {
		case JPG:
			return ".jpg";
		case JPEG:
			return ".jpeg";
		case PNG:
			return ".png";
		case PDF:
			return ".pdf";
		default:
			throw new IllegalArgumentException();
		}
	}
}
