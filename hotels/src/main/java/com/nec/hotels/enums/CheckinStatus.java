package com.nec.hotels.enums;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Maps;

public enum CheckinStatus {
	PENDING(0, "Pending"), SUCCESS(1, "Success"), FAILURE(2, "Failure");

	private Integer value;
	private String description;

	private static final Map<Integer, CheckinStatus> CODE_TO_TYPE = Maps.newHashMap();

	private static final Map<String, CheckinStatus> TYPE_TO_CODE = Maps.newHashMap();

	private CheckinStatus(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	static {
		for (final CheckinStatus CheckinStatus : CheckinStatus.values()) {
			CODE_TO_TYPE.put(CheckinStatus.getCode(), CheckinStatus);
		}
	}

	static {
		for (final CheckinStatus CheckinStatus : CheckinStatus.values()) {
			TYPE_TO_CODE.put(CheckinStatus.getDescription(), CheckinStatus);
		}
	}

	public Integer getCode() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static CheckinStatus idToType(final Integer code) {
		return CODE_TO_TYPE.get(code);
	}

	@JsonCreator
	public static CheckinStatus TypeToId(final String description) {
		return TYPE_TO_CODE.get(description);
	}

	@Override
	public String toString() {
		switch (this) {
		case SUCCESS:
			return "Success";
		case FAILURE:
			return "Failure";
		case PENDING:
			return "Pending";
		default:
			throw new IllegalArgumentException();
		}
	}
}

