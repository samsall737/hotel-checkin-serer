package com.nec.ocr.model;

	import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
	import com.fasterxml.jackson.annotation.JsonProperty;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class OCRResponse {

		@JsonProperty("name")
		private String name;
		
		@JsonProperty("doc_number")
		private String docNumber;
		
		@JsonProperty("date_of_birth")
		private String dateOfBirth;
		
		
		@JsonProperty("date_of_expiry")
		private String dateOfExpiry;
		
		@JsonProperty("sub_type")
		private String subType;
		
		@JsonProperty("sub_type_number")
		private String subTypeNumber;
		
		@JsonProperty("face_match")
		private String faceMatch;
		
		@JsonProperty("face_data")
		private String faceData;
		
		@JsonProperty("unique_id")
		private String uniqueId;
		
		@JsonProperty("customer_id")
		private String customerId;
		
		@JsonProperty("status")
		private String status;
		
		@JsonProperty("msg")
		private String msg;
		
		@JsonProperty("address")
		private String address;
		
		@JsonProperty("nationality")
		private String nationality;

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
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

		public String getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
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

		public String getUniqueId() {
			return uniqueId;
		}

		public void setUniqueId(String uniqueId) {
			this.uniqueId = uniqueId;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDateOfExpiry() {
			return dateOfExpiry;
		}

		public void setDateOfExpiry(String dateOfExpiry) {
			this.dateOfExpiry = dateOfExpiry;
		}
		
		
	}

