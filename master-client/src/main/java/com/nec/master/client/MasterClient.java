package com.nec.master.client;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestParam;

import com.nec.master.model.Hotel;
import com.nec.master.model.User;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MasterClient {

	@GET("/api/v1/hotel/{hotel_id}")
	Single<Response<Hotel>> getHotelById(@Path(value = "hotel_id") UUID hotelId);

	@GET("/api/v1/booking/{booking_id}/hotel")
	Single<Response<Hotel>> getHotelByBookingId(@Path(value = "booking_id") String bookingId);

	@GET("/api/v1/hotel/admin")
	Single<Response<Hotel>> getHotelByAdminEmail(@Query(value = "email") String email);
	
	@GET("/api/v1/user")
	Single<Response<User>> getAdminByEmail(@QueryMap HashMap<String, String> queryParams);
	
	@GET("/api/v1/user/{id}")
	Single<Response<User>> getAdminById(@Path(value = "id") String id);

	@POST("/api/v1/hotel")
	Single<Response<Hotel>> getHotel(@Body Hotel hotel);

	@GET("/api/v1/hotel/pms")
	Single<Response<Hotel>> getHotelByHotelCode(@Query(value = "hotelCode") String hotelCode);

	@GET("/api/v1/hotel")
	Single<Response<List<Hotel>>> getAllHotels();
	

	@POST("/api/v1/user/login")
	Single<Response<User>> loginUser(@Body User user);
	
	@POST("/api/v1/user")
	Single<Response<User>> registerUser(@Body User user);
	
	@PUT("/api/v1/hotel/token")
	Single<Response<Void>> updateToken(@Query(value = "accessToken") String accessToken, @Query(value ="hotelId") UUID hotelId);
}