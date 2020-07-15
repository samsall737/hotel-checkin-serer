package com.nec.hotels.model.booking;

import com.nec.master.model.Hotel;

public class BookingResponse {

	BookingDTO booking;
	Hotel hotel;

	public BookingResponse(BookingDTO booking, Hotel hotel) {
		super();
		this.booking = booking;
		this.hotel = hotel;
	}

	public BookingDTO getBooking() {
		return booking;
	}

	public void setBooking(BookingDTO booking) {
		this.booking = booking;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
