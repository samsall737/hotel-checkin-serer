package com.nec.pms.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxesAndFee {

	private UUID id;

	@JsonProperty("amount")
	private String amount;
	
	@JsonProperty("description")
	private String description;

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("codeType")
	private String codeType;

	
}
