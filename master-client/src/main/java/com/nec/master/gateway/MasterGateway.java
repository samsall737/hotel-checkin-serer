package com.nec.master.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import com.nec.exception.HotelCheckinException;
import com.nec.master.client.MasterClient;
import com.nec.master.model.Hotel;
import com.nec.master.model.User;

import retrofit2.Response;

@Component
public class MasterGateway {

	@Autowired
	private MasterClient masterClient;

	/**
	 * 
	 * @param hotelId
	 * @return
	 */
	public Hotel getHotelById(UUID hotelId) {
		Response<Hotel> hotelResponse = masterClient.getHotelById(hotelId).blockingGet();
		if (hotelResponse.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!hotelResponse.isSuccessful()) {
			throw new HotelCheckinException("Hotel Id not Registered", HttpStatus.BAD_REQUEST);
		}
		return hotelResponse.body();
	}

	/**
	 * 
	 * @param bookingId
	 * @return
	 */
	public Hotel getHotelByBookingId(String bookingId) {
		Response<Hotel> response = masterClient.getHotelByBookingId(bookingId).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public Hotel getHotelByAdminEmail(String email) {
		Response<Hotel> response = masterClient.getHotelByAdminEmail(email).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}
	
	/**
	 * Login Admin
	 * @param user
	 * @return
	 */
	public Optional<User> loginUser(User user) {
		Response<User> response = masterClient.loginUser(user).blockingGet();
		if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
			throw new HotelCheckinException("Invalid email id or password", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return Optional.of(response.body());
	}
	
	/**
	 * Register Admin
	 * @param user
	 * @return
	 */
	public Optional<User> registerUser(User user) {
		Response<User> response = masterClient.registerUser(user).blockingGet();
		if (response.code() == HttpStatus.CONFLICT.value()) {
			throw new HotelCheckinException("User Already register", HttpStatus.CONFLICT);
		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return Optional.of(response.body());
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public User getAdminByEmail(String email) {
		HashMap<String, String> map = new HashMap<>();
		map.put("email", email);
		Response<User> response = masterClient.getAdminByEmail(map).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("User/Admin Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}
	
	/**
	 * 
	 * @param email
	 * @param userName
	 * @return
	 */
	public Optional<User> findByEmailOrUserName(String email, String userName) {
		HashMap<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("username", userName);
		Response<User> response = masterClient.getAdminByEmail(map).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("User/Admin Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return Optional.of(response.body());
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getAdminById(String id) {
		Response<User> response = masterClient.getAdminById(id).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("User/Admin Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}
	
	/**
	 * 
	 * @param hotel
	 * @return
	 */
	public Hotel getHotel(Hotel hotel) {
		Response<Hotel> response = masterClient.getHotel(hotel).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);

		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @param hotelCode
	 * @return
	 */
	public Hotel getHotelByHotelCode(String hotelCode) {
		Response<Hotel> response = masterClient.getHotelByHotelCode(hotelCode).blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);
		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @return
	 */
	public List<Hotel> getAllHotels() {
		Response<List<Hotel>> response = masterClient.getAllHotels().blockingGet();
		if (response.code() == HttpStatus.NO_CONTENT.value()) {
			throw new HotelCheckinException("Hotel Not Found", HttpStatus.BAD_REQUEST);
		}
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(response.message(), HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	public void saveAccessToken(String token, UUID hotelId) {
		Response<Void> response = masterClient.updateToken(token, hotelId).blockingGet();	
	}
	


	

	

}
