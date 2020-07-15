package com.nec.hotels.enums;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Maps;

public enum PreCheckinStatus {
	PENDING(0, "Pending"),ATTEMPTED(1, "Attempted"), SUCCESS(2, "Success"), FAILURE(3, "Failure");

	private Integer value;
	private String description;

	private static final Map<Integer, PreCheckinStatus> CODE_TO_TYPE = Maps.newHashMap();

	private static final Map<String, PreCheckinStatus> TYPE_TO_CODE = Maps.newHashMap();

	private PreCheckinStatus(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	static {
		for (final PreCheckinStatus preCheckinStatus : PreCheckinStatus.values()) {
			CODE_TO_TYPE.put(preCheckinStatus.getCode(), preCheckinStatus);
		}
	}

	static {
		for (final PreCheckinStatus preCheckinStatus : PreCheckinStatus.values()) {
			TYPE_TO_CODE.put(preCheckinStatus.getDescription(), preCheckinStatus);
		}
	}

	public Integer getCode() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static PreCheckinStatus idToType(final Integer code) {
		return CODE_TO_TYPE.get(code);
	}

	@JsonCreator
	public static PreCheckinStatus TypeToId(final String description) {
		return TYPE_TO_CODE.get(description);
	}

	@Override
	public String toString() {
		switch (this) {
		case SUCCESS:
			return "Success";
		case FAILURE:
			return "Failure";
		case ATTEMPTED:
			return "Attempted";
		case PENDING:
			return "Unattempted";
		default:
			throw new IllegalArgumentException();
		}
	}
}
