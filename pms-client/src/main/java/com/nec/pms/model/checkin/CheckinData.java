package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckinData {

	@JsonProperty("attributes")
	private Object attributes;
	
	@JsonProperty("checkInComplete")
	private CheckInComplete checkInComplete;
	
	@JsonProperty("profile")
	private Profile profile;
	
	@JsonProperty("attributes")
	public Object getAttributes() {
		return attributes;
	}
	
	@JsonProperty("attributes")
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	
	@JsonProperty("checkInComplete")
	public CheckInComplete getCheckInComplete() {
		return checkInComplete;
	}
	
	@JsonProperty("checkInComplete")
	public void setCheckInComplete(CheckInComplete checkInComplete) {
		this.checkInComplete = checkInComplete;
	}
	
	@JsonProperty("profile")
	public Profile getProfile() {
		return profile;
	}
	
	@JsonProperty("profile")
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}