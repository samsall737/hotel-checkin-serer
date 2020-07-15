package com.nec.hotels.model.booking;


public class RoomInclusions {

	private String inclusion;

	public RoomInclusions() {
	}

	public RoomInclusions(String inclusion) {
		super();
		this.inclusion = inclusion;
	}

	public String getInclusion() {
		return inclusion;
	}

	public void setInclusion(String inclusion) {
		this.inclusion = inclusion;
	}
}