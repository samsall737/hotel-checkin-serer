package com.nec.master.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Hotel {

	private UUID id;

	@NotBlank
	private String name;

	private Address address;

	@NotBlank
	@JsonProperty("image_url")
	private String imageUrl;

	@NotBlank
	@JsonProperty("terms_and_conditions")
	private String termsAndConditions;

	@NotNull
	@JsonProperty(value = "database_connection", access = JsonProperty.Access.WRITE_ONLY)
	private DBConnection dBConnection;

	@NotBlank
	@JsonProperty(value = "service_name", access = JsonProperty.Access.WRITE_ONLY)
	private String serviceName;

	@JsonProperty("hotel_code")
	private String hotelCode;
	
	@JsonProperty(value = "group_id" , access = JsonProperty.Access.WRITE_ONLY)
	private int groupId;


	@JsonProperty(value = "pms_base_url", access = JsonProperty.Access.WRITE_ONLY)
	private String pmsBaseUrl;
	
	@JsonProperty("timezone")
	private String timezone;
	
	@JsonProperty("chain")
	private Chain chains;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Configuration configuration;
	
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	@JsonIgnore
	public DBConnection getDatabaseConnections() {
		return dBConnection;
	}

	public void setDatabaseConnections(DBConnection dBConnection) {
		this.dBConnection = dBConnection;
	}

	@JsonIgnore
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	@JsonIgnore
	public String getPmsBaseUrl() {
		return pmsBaseUrl;
	}

	public void setPmsBaseUrl(String pmsBaseUrl) {
		this.pmsBaseUrl = pmsBaseUrl;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Configuration getConfiguration() {
		if(Objects.isNull(configuration)) {
			return new Configuration();
		}
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Chain getChains() {
		return chains;
	}

	public void setChains(Chain chains) {
		this.chains = chains;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
}

