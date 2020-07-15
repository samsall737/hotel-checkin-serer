package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Checkin {

	@JsonProperty("cardType")
	private String cardType;
	@JsonProperty("cardNumber")
	private String cardNumber;
	@JsonProperty("cardHolderName")
	private String cardHolderName;
	@JsonProperty("expirationDate")
	private String expirationDate;
	@JsonProperty("signedDocument")
	private String signedDocument;

	@JsonProperty("cardType")
	public String getCardType() {
		return cardType;
	}

	@JsonProperty("cardType")
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@JsonProperty("cardNumber")
	public String getCardNumber() {
		return cardNumber;
	}

	@JsonProperty("cardNumber")
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@JsonProperty("cardHolderName")
	public String getCardHolderName() {
		return cardHolderName;
	}

	@JsonProperty("cardHolderName")
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	@JsonProperty("expirationDate")
	public String getExpirationDate() {
		return expirationDate;
	}

	@JsonProperty("expirationDate")
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	@JsonProperty("signedDocument")
	public String getSignedDocument() {
		return signedDocument;
	}

	@JsonProperty("signedDocument")
	public void setSignedDocument(String signedDocument) {
		this.signedDocument = signedDocument;
	}

}