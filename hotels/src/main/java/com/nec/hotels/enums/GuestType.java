package com.nec.hotels.enums;


public enum GuestType {
	PRIMARY(1), ACCOMPANY_GUEST(2), SHARED_GUEST(3), KIDS(4);

	private int type;

	private GuestType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	
	
}
