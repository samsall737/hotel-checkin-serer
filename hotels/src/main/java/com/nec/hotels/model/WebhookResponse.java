package com.nec.hotels.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookResponse {

	@JsonProperty("unique_id")
	private String uniqueId;

	private String name;

	@JsonProperty("doc_number")
	private String docNumber;

	@JsonProperty("sub_type")
	private String subType;

	@JsonProperty("sub_type_number")
	private String subTypeNumber;

	@JsonProperty("face_match")
	private String faceMatch;

	@JsonProperty("face_data")
	private String faceData;

	@JsonProperty("customer_id")
	private UUID customerId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeNumber() {
		return subTypeNumber;
	}

	public void setSubTypeNumber(String subTypeNumber) {
		this.subTypeNumber = subTypeNumber;
	}

	public String getFaceMatch() {
		return faceMatch;
	}

	public void setFaceMatch(String faceMatch) {
		this.faceMatch = faceMatch;
	}

	public String getFaceData() {
		return faceData;
	}

	public void setFaceData(String faceData) {
		this.faceData = faceData;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		try {
			this.customerId = UUID.fromString(customerId);
		}catch(IllegalArgumentException e){
			this.customerId = null;
		}
	}

	@Override
	public String toString() {
		return "WebhookResponse [uniqueId=" + uniqueId + ", name=" + name + ", docNumber=" + docNumber + ", subType="
				+ subType + ", subTypeNumber=" + subTypeNumber + ", faceMatch=" + faceMatch + ", faceData=" + faceData
				+ ", customerId=" + customerId + "]";
	}

}
