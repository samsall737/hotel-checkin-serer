package com.nec.pms.model.guest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nameTitle", "firstName", "middleName", "lastName" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Name {

	@JsonProperty("nameTitle")
	private String nameTitle;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("middleName")
	private String middleName;
	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("nameTitle")
	public String getNameTitle() {
		return nameTitle;
	}

	@JsonProperty("nameTitle")
	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
