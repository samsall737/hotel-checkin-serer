
package com.nec.hotels.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nec.ocr.enums.DocumentType;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DocumentTypes {
	PASSPORT("passport"), AADHAR_CARD("aadhar card"), DRIVING_LICENCE("driving licence"), VISA("visa");

	private String type;
	private static final Map<String, DocumentTypes> types = new HashMap<>();

	private DocumentTypes(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	static {
		for (final DocumentTypes documentTypes : DocumentTypes.values()) {
			types.put(documentTypes.getType(), documentTypes);
		}
	}
	
	public static DocumentType getDocumentType(String docType) {
		switch (docType) {
		case "passport":
			return DocumentType.PASSPORT ;
		case "aadhar card":
			return DocumentType.AADHAR_CARD ;
		case "driving licence":
			return DocumentType.AADHAR_CARD;
		case "visa":
			return DocumentType.VISA;
		default:
			throw new IllegalArgumentException();
		}
		
		
	}

	public static boolean contains(String type) {
		return types.containsKey(type.toLowerCase());
	}

	@Override
	public String toString() {
		switch (this) {
		case PASSPORT:
			return "passport";
		case AADHAR_CARD:
			return "aadhar card";
		case DRIVING_LICENCE:
			return "driving licence";
		case VISA:
			return "visa";
		default:
			throw new IllegalArgumentException();
		}
	}
}
