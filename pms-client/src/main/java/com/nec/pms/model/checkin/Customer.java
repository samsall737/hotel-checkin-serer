package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.pms.model.guest.Name;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

	@JsonProperty("attributes")
	private Object attributes;
	
	@JsonProperty("personName")
	private Name personName;
	
	@JsonProperty("nativeName")
	private Object nativeName;
	
	@JsonProperty("attributes")
	public Object getAttributes() {
		return attributes;
	}
	
	@JsonProperty("attributes")
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	
	@JsonProperty("personName")
	public Name getPersonName() {
		return personName;
	}
	
	@JsonProperty("personName")
	public void setPersonName(Name personName) {
		this.personName = personName;
	}
	
	@JsonProperty("nativeName")
	public Object getNativeName() {
		return nativeName;
	}
	
	@JsonProperty("nativeName")
	public void setNativeName(Object nativeName) {
		this.nativeName = nativeName;
	}

}