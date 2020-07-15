package com.nec.hotels.enums;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Maps;

public enum Title {
	MR(0, "MR"), MS(1, "MS"), MRS(2, "MRS"), MX(3, "MX");

	private Integer value;
	
	private String description;

	private static final Map<Integer, Title> CODE_TO_TYPE = Maps.newHashMap();

	private static final Map<String, Title> TYPE_TO_CODE = Maps.newHashMap();

	private Title(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	static {
		for (final Title title : Title.values()) {
			CODE_TO_TYPE.put(title.getCode(), title);
		}
	}

	static {
		for (final Title title : Title.values()) {
			TYPE_TO_CODE.put(title.getDescription(), title);
		}
	}

	public Integer getCode() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static Title idToType(final Integer code) {
		return CODE_TO_TYPE.get(code);
	}

	@JsonCreator
	public static Title typeToId(final String description) {
		return TYPE_TO_CODE.get(description);
	}

	@Override
	public String toString() {
		switch (this) {
		case MR:
			return "Mr.";
		case MS:
			return "Ms.";
		case MRS:
			return "Mrs.";
		case MX:
			return "Mx";
		default:
			throw new IllegalArgumentException();
		}
	}
}
