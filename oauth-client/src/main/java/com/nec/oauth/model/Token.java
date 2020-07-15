package com.nec.oauth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Token {
	private final String id;
	private final String userAgent;
	private final String accessToken;
	private final String refreshToken;
	private final String userType;
	private final String serviceUrl;
	private final Long expireInMiliSec;

	public Token() {
		this(null, null, null, null, null, null, null);
	}

	public Token(final String userId, final String userAgent, final String accessToken, final String userType,
			final Long expireInMiliSec, final String refreshToken, final String serviceUrl) {
		this.id = userId;
		this.userAgent = userAgent;
		this.accessToken = accessToken;
		this.userType = userType;
		this.expireInMiliSec = expireInMiliSec;
		this.refreshToken = refreshToken;
		this.serviceUrl = serviceUrl;
	}

	@JsonProperty("user_id")
	public String getId() {
		return id;
	}

	@JsonProperty("user_agent")
	public String getUserAgent() {
		return userAgent;
	}

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty("user_type")
	public String getUserType() {
		return userType;
	}

	@JsonProperty("refresh_token")
	public String getRefreshToken() {
		return refreshToken;
	}

	@JsonProperty("expire_time_mili_sec")
	public Long getExpireInMiliSec() {
		return expireInMiliSec;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", userAgent=" + userAgent + ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + ", userType=" + userType + ", expireInMiliSec=" + expireInMiliSec + "]";
	}
}
