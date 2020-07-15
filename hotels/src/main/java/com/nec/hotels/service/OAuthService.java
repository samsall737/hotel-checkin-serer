package com.nec.hotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.oauth.gateway.AuthGateway;
import com.nec.oauth.model.Token;


@Service
public class OAuthService {
	
	
	private final AuthGateway oAuthGateway;
	
	@Autowired
	public OAuthService(final AuthGateway oAuthGateway) {
		this.oAuthGateway = oAuthGateway;
	}
	
	/**
	 * Generate Token
	 * @param hotelId
	 * @param userAgent
	 * @param serviceName
	 * @return
	 */
	public Token generateAccessToken(String hotelId, String userAgent, String serviceName) {
		return oAuthGateway.generateAccessToken(new Token(hotelId, userAgent, null, null, null, null, serviceName));
	}

	/**
	 * verify Token
	 * @param userId
	 * @param role
	 * @param userAgent
	 * @return
	 */
	public Token generateAuthToken(String userId, String role, String userAgent) {
		return oAuthGateway.generateAuthToken(new Token(userId, userAgent, null, role, null, null, null));
	}
	

	/**
	 * verify Token
	 * @param userId
	 * @param role
	 * @param userAgent
	 * @return
	 */
	public Token verifyAndDecodeAuthToken(String userId, String userAgent, String authToken) {
		return oAuthGateway.verifyAndDecodeAuthToken(new Token(userId, userAgent, authToken, null, null, null, null));
	}
	
	public Token verifyAndDecodeAccessToken(String userId, String userAgent, String accessToken) {
		return oAuthGateway.verifyAndDecodeAccessToken(new Token(userId, userAgent, accessToken, null, null, null, null));
	}

}
