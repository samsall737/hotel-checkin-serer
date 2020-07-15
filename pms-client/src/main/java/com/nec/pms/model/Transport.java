package com.nec.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "time", "transportationRequired" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transport {

	@JsonProperty("time")
	private String time;
	
	@JsonProperty("transportationRequired")
	private Boolean transportationRequired;

	@JsonProperty("carrierCode")
	private String carrierCode;

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	@JsonProperty("time")
	public String getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(String time) {
		this.time = time;
	}

	@JsonProperty("transportationRequired")
	public Boolean getTransportationRequired() {
		return transportationRequired;
	}

	@JsonProperty("transportationRequired")
	public void setTransportationRequired(Boolean transportationRequired) {
		this.transportationRequired = transportationRequired;
	}

}
