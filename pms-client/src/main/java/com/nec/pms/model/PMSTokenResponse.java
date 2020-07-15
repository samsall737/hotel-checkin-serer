package com.nec.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PMSTokenResponse {
	
	@JsonProperty("data")
	private PMSToken data;

	public PMSToken getData() {
		return data;
	}

	public void setData(PMSToken data) {
		this.data = data;
	}
	
	

}
