package com.nec.pms.model.reservation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmsReservationsDetails {
	
	@JsonProperty("success")
	private Boolean success;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("data")
	private List<ReservationsData> data;
	
	@JsonProperty("status")
	private Integer status;
	
	@JsonProperty("action")
	private String action;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("type")
	private String type;
	
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ReservationsData> getData() {
		return data;
	}
	public void setData(List<ReservationsData> data) {
		this.data = data;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
