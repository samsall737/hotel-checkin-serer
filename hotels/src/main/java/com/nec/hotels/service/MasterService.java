package com.nec.hotels.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.master.gateway.MasterGateway;
import com.nec.master.model.Hotel;
import com.nec.master.model.User;

@Service
public class MasterService {
	
	public final MasterGateway gateway;
	
	@Autowired
	public MasterService(final MasterGateway masterGateway) {
		this.gateway = masterGateway;
	}
	
	public Hotel getHotelByHotelCode(String hotelCode) {
		return gateway.getHotelByHotelCode(hotelCode);
	}
	
	public Hotel getHotel(UUID hotelId) {
		return gateway.getHotelById(hotelId);
	}
	
	public User getUser(String id) {
		return gateway.getAdminById(id);
	}
	
	public List<Hotel> getAllHotels(){
		return gateway.getAllHotels();
	}
	

}
