package com.nec.master.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chain {


	private UUID id;
	private String name;
	
	@JsonProperty("brand_name")
	private String brandName;
	
	@JsonProperty("chain_code")
	private String chainCode;
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getChainCode() {
		return chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}
	
}