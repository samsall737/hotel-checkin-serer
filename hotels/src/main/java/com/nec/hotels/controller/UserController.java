package com.nec.hotels.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nec.hotels.service.OAuthService;
import com.nec.hotels.service.UserService;
import com.nec.hotels.utils.Constants;
import com.nec.master.model.Hotel;
import com.nec.master.model.User;
import com.nec.oauth.model.Token;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	
	private final UserService userService;
	private final OAuthService authService;
	
	@Autowired
	public UserController(final UserService userService, final OAuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}


	/*
	 * This function is used for login of Admin
	 */
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@Valid @RequestBody User user,@RequestHeader(Constants.HEADER_USER_AGENT) String userAgent) {
		LOGGER.info("==================adminLogin============== {}", user);
		User userDB = userService.login(user);
		Hotel hotel = userService.getHotelById(userDB.getHotelId());
		HttpHeaders headers = getHeaders(userDB, userAgent, hotel);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userDB);
	}

	/**
	 * generate auth header
	 * @param user
	 * @param userAgent
	 * @return
	 */
	private HttpHeaders getHeaders(User user, String userAgent, Hotel hotel) {
		HttpHeaders headers = new HttpHeaders();
		Token auth = authService.generateAuthToken(user.getId().toString(), String.valueOf(user.getType()), userAgent);
		Token access = authService.generateAccessToken(user.getHotelId().toString(), userAgent, hotel.getServiceName());
		headers.add(Constants.HEADER_AUTHORIZATION, auth.getAccessToken());
		headers.add(Constants.HEADER_ACCESS_TOKEN, access.getAccessToken());
		headers.add(Constants.HEADER_USERID, user.getId().toString());
		headers.add(Constants.HEADER_ACCESS_REFRESH_TOKEN_, access.getRefreshToken());
		headers.add(Constants.HEADER_AUTHORIZATION_REFRESH, auth.getRefreshToken());
		return headers;
	}

	
	@PostMapping("/register")
	public ResponseEntity<User> register(@Valid @RequestBody User user,@RequestHeader(Constants.HEADER_USER_AGENT) String userAgent) {
		User userDB = userService.registerUser(user);
		Hotel hotel = userService.getHotelById(userDB.getHotelId());
		HttpHeaders headers = getHeaders(userDB, userAgent, hotel);
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(userDB);
	}




}
