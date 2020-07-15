package com.nec.hotels.enums;


public enum UserType {
	
	USER(1), HOTEL_ADMIN(2), SUPER_ADMIN(3);

	private int value;


	private UserType(Integer value) {
		this.value = value;
	}

	public int getCode() {
		return value;
	}

	public static UserType valueOf(int type) {
		switch (type) {
		case 1:
			return USER;
		case 2:
			return HOTEL_ADMIN;
		case 3:
			return SUPER_ADMIN;
		default:
			throw new IllegalArgumentException();
		}
	}


	@Override
	public String toString() {
		switch (this) {
		case USER:
			return "User";
		case HOTEL_ADMIN:
			return "HotelAdmin";
		case SUPER_ADMIN:
			return "SuperAdmin";
		default:
			throw new IllegalArgumentException();
		}
	}

}
