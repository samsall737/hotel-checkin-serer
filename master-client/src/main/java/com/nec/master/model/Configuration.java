package com.nec.master.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {

	private UUID id;
	
	@JsonProperty("ocr_active")
	private boolean ocrActive;
	
	@JsonProperty("pms_active")
	private boolean pmsActive;

	@JsonProperty("face_recognition_active")
	private boolean faceRecognitionActive;

	@JsonProperty("poll_period")
	private long pollPeriod;
	
	@JsonProperty("email_username")
	private String emailUserName;
	
	@JsonProperty("email_password")
	private String emailPassword;

	@JsonProperty("kids_age")
	private int kidsAge;

	@JsonProperty("signature_position_x")
	private int signaturePositionX;
	
	@JsonProperty("signature_position_y")
	private int signaturePositionY;
	
	@JsonProperty("sync_start_days")
	private Integer syncStartDays;
	
	@JsonProperty("sync_end_days")
	private Integer syncEndDays;

	private String pmsId;
	
	private String password;
	
	private String source;

	private String systemType;
	
	private String user;
	
	private String token;
	
	@JsonProperty("host")
	private String pmsHost;
	
	public void setPmsId(String pmsId) {
		this.pmsId = pmsId;
	}

	public String getPmsId() {
		return pmsId;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getSyncStartDays() {
		return syncStartDays;
	}

	public void setSyncStartDays(Integer syncStartDays) {
		this.syncStartDays = syncStartDays;
	}

	public int getSyncEndDays() {
		return syncEndDays;
	}

	public void setSyncEndDays(Integer syncEndDays) {
		this.syncEndDays = syncEndDays;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public boolean isOcrActive() {
		return ocrActive;
	}

	public void setOcrActive(boolean ocrActive) {
		this.ocrActive = ocrActive;
	}

	public boolean isPmsActive() {
		return pmsActive;
	}

	public void setPmsActive(boolean pmsActive) {
		this.pmsActive = pmsActive;
	}

	public boolean isFaceRecognitionActive() {
		return faceRecognitionActive;
	}

	public void setFaceRecognitionActive(boolean faceRecognitionActive) {
		this.faceRecognitionActive = faceRecognitionActive;
	}

	public long getPollPeriod() {
		return pollPeriod;
	}

	public void setPollPeriod(long pollPeriod) {
		this.pollPeriod = pollPeriod;
	}

	public String getEmailUserName() {
		return emailUserName;
	}

	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public int getKidsAge() {
		return kidsAge;
	}

	public void setKidsAge(int kidsAge) {
		this.kidsAge = kidsAge;
	}

	public int getSignaturePositionX() {
		return signaturePositionX;
	}

	public void setSignaturePositionX(int signaturePositionX) {
		this.signaturePositionX = signaturePositionX;
	}

	public int getSignaturePositionY() {
		return signaturePositionY;
	}

	public void setSignaturePositionY(int signaturePositionY) {
		this.signaturePositionY = signaturePositionY;
	}

	public String getPmsHost() {
		return pmsHost;
	}

	public void setPmsHost(String pmsHost) {
		this.pmsHost = pmsHost;
	}
	
	
}