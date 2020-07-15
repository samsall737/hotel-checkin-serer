package com.nec.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PMSTokenRequest {
	
	@JsonProperty("pmsId")
	private String pmsId;
	
	@JsonProperty("password")
	private String password; 
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("systemType")
	private String systemType;
	
	@JsonProperty("hotelCode")
	private String hotelCode;
	
	@JsonProperty("chainCode")
	private String chainCode;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("host")
	private String pmsHost;

	public PMSTokenRequest(String pmsId, String password, String source, String systemType, String hotelCode,
			String chainCode, String user, String pmsHost) {
		this.pmsId = pmsId;
		this.password = password;
		this.source = source;
		this.systemType = systemType;
		this.hotelCode = hotelCode;
		this.chainCode = chainCode;
		this.user = user;
		this.pmsHost = pmsHost;
	}

	public String getPmsId() {
		return pmsId;
	}

	public void setPmsId(String pmsId) {
		this.pmsId = pmsId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getChainCode() {
		return chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPmsHost() {
		return pmsHost;
	}

	public void setPmsHost(String pmsHost) {
		this.pmsHost = pmsHost;
	}


}
