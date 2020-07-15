package com.nec.hotels.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nec.exception.HotelCheckinException;
import com.nec.master.gateway.MasterGateway;
import com.nec.master.model.Hotel;
import com.nec.master.model.User;

@Service
public class UserService {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(UserService.class);
	
	public final MasterGateway masterGateway;
	
	@Autowired
	public UserService(final MasterGateway masterGateway) {
		this.masterGateway = masterGateway;
	}
	
	
	public User login(User user) {
		LOGGER.info("==================register============== {}", user);
		Optional<User> userDB = masterGateway.loginUser(user);
		if (!userDB.isPresent()) {
			throw new HotelCheckinException("Admin not found with provided email address", HttpStatus.CONFLICT);
		}
		return userDB.get();
	}
	
	
	/*
	 * This function is to register an Admin
	 */
	public User registerUser(User user) {
		LOGGER.info("==================register============== {}", user);
		Optional<User> userDB = masterGateway.registerUser(user);
		if (userDB.isPresent()) {
			throw new HotelCheckinException("Username or Email Already Exists", HttpStatus.CONFLICT);
		}
		return userDB.get();
	}
	
	/*
	 * This function is use to fetch hotel details
	 */
	public Hotel getHotelById(UUID hotelId) {
		LOGGER.info("==================register============== {}", hotelId);
		return masterGateway.getHotelById(hotelId);
	}

}
