package com.nec.pms.model.regcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.pms.model.reservation.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegCardData {

	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private Data data;
//	@JsonProperty("metadata")
//	private Object metadata;
	@JsonProperty("status")
	private Integer status;
//	@JsonProperty("action")
//	private Object action;
//	@JsonProperty("signature")
//	private Object signature;
	@JsonProperty("type")
	private String type;
//	@JsonProperty("id")
//	private Object id;

	@JsonProperty("success")
	public Boolean getSuccess() {
		return success;
	}

	@JsonProperty("success")
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("data")
	public Data getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(Data data) {
		this.data = data;
	}

	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

}