package com.nec.pms.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomRatesAndPackage {

	private UUID id;

	@JsonProperty("amount")
	private String amount;

	@JsonProperty("description")
	private String description;

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("isAddon")
	private boolean isAddon;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isAddon() {
		return isAddon;
	}

	public void setAddon(boolean isAddon) {
		this.isAddon = isAddon;
	}
	
	
}
