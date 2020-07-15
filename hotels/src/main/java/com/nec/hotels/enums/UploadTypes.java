package com.nec.hotels.enums;

import java.util.HashMap;
import java.util.Map;

public enum UploadTypes {
	IMAGE("image"), DOCUMENT_FRONT("document_front"), DOCUMENT_BACK("document_back"), SIGNATURE("signature"),
	VISA("visa");

	private String description;
	private static final Map<String, UploadTypes> types = new HashMap<>();

	private UploadTypes(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	static {
		for (final UploadTypes uploadType : UploadTypes.values()) {
			types.put(uploadType.getDescription(), uploadType);
		}
	}

	public static boolean contains(String type) {
		return types.containsKey(type.toLowerCase());
	}

	@Override
	public String toString() {
		switch (this) {
		case IMAGE:
			return "image";
		case DOCUMENT_FRONT:
			return "document_front";
		case DOCUMENT_BACK:
			return "document_back";
		case SIGNATURE:
			return "signature";
		case VISA:
			return "visa";
		default:
			throw new IllegalArgumentException();
		}
	}
}
