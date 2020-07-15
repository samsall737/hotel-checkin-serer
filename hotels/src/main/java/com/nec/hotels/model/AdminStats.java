package com.nec.hotels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminStats {

	@JsonProperty("total_guests")
	private Integer totalGuest;

	@JsonProperty("pre_checkin_success")
	private Integer preCheckinSuccess;

	@JsonProperty("pre_checkin_failure")
	private Integer preCheckinFailure;

	@JsonProperty("checkin_success")
	private Integer checkinSuccess;

	@JsonProperty("pre_checkin_attempted")
	private Integer preCheckinAttempted;

	public int getCheckinSuccess() {
		return checkinSuccess;
	}

	public void setCheckinSuccess(Integer checkinSuccess) {
		this.checkinSuccess = checkinSuccess;
	}

	public int getTotalGuest() {
		return totalGuest;
	}

	public void setTotalGuest(Integer totalGuest) {
		this.totalGuest = totalGuest;
	}

	public int getPreCheckinSuccess() {
		return preCheckinSuccess;
	}

	public void setPreCheckinSuccess(Integer preCheckinSuccess) {
		this.preCheckinSuccess = preCheckinSuccess;
	}

	public int getPreCheckinFailure() {
		return preCheckinFailure;
	}

	public void setPreCheckinFailure(Integer preCheckinFailure) {
		this.preCheckinFailure = preCheckinFailure;
	}

	public int getPreCheckinAttempted() {
		return preCheckinAttempted;
	}

	public void setPreCheckinAttempted(Integer preCheckinAttempted) {
		this.preCheckinAttempted = preCheckinAttempted;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (this.totalGuest == 0);
	}
}
