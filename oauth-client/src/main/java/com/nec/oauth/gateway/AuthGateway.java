package com.nec.oauth.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.springframework.stereotype.Component;

import com.nec.exception.HotelCheckinException;
import com.nec.oauth.client.AuthClient;
import com.nec.oauth.model.Token;

import retrofit2.Response;

@Component
public class AuthGateway {

	@Autowired
	private AuthClient authClient;
	
	
	public Token verifyAndDecodeAccessToken(Token token){
	Response<Token> response = authClient
			.verifyAndDecodeAccessToken(token)
			.blockingGet();
	if (!response.isSuccessful()) {
		throw new HotelCheckinException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
	}
	return response.body();
	}

	public Token generateAccessToken(Token token) {
		Response<Token> response = authClient
				.generateAccessToken(token).blockingGet();
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
		}
		return response.body();
	}

	public Token generateAuthToken(Token token) {
		Response<Token> response = authClient
				.generateAuthToken(token).blockingGet();
		if (!response.isSuccessful()) {
			throw new HotelCheckinException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
		}
		return response.body();
	}
	
	public Token verifyAndDecodeAuthToken(Token token) {
		final Response<Token> response = authClient
				.verifyAndDecodeAuthToken(token)
				.blockingGet();
		if (!response.isSuccessful()) {
			return null;
		}
		return response.body();
	}
}
