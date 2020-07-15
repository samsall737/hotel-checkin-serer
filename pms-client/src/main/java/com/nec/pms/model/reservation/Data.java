package com.nec.pms.model.reservation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.nec.pms.model.ExpectedCharges;
import com.nec.pms.model.FlightDetails;
import com.nec.pms.model.RoomDetail;
import com.nec.pms.model.guest.AccompanyGuest;
import com.nec.pms.model.guest.PrimaryGuest;
import com.nec.pms.model.guest.SharedGuest;
import com.nec.pms.model.guest.Timespan;
import com.nec.pms.model.regcard.DocumentToSign;
import com.nec.pms.utils.PhoneNumberLibUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	@JsonProperty("refernceCurrency")
	private String refernceCurrency;
	
	@JsonProperty("roomDetails")
	private List<RoomDetail> roomDetails = null;
	
	@JsonProperty("expectedCharges")
	private ExpectedCharges expectedCharges;
	
	@JsonProperty("vipCode")
	private String vipCode;
	
	@JsonProperty("confirmationId")
	private String confirmationId;
	
	@JsonProperty("flightDetails")
	private FlightDetails flightDetails;
	
	@JsonProperty("childCount")
	private String childCount;
	
	@JsonProperty("adultCount")
	private String adultCount;
	
	@JsonProperty("primaryGuest")
	private PrimaryGuest primaryGuest;
	
	@JsonProperty("accompanyGuests")
	private List<AccompanyGuest> accompanyGuests;
	
	@JsonProperty("sharedReservations")
	private List<SharedGuest> sharedReservations;
	
	@JsonProperty("emails")
	private List<String> emails = null;
	
	@JsonProperty("phoneNumber")
	private List<String> phoneNumber = null;
	
	private Timespan timespan;
	
	@JsonProperty("nationality")
	private String nationality;
	
	@JsonProperty("reservationStatus")
	private String reservationStatus;

	@JsonProperty("refernceCurrency")
	public String getRefernceCurrency() {
		return refernceCurrency;
	}

	@JsonProperty("refernceCurrency")
	public void setRefernceCurrency(String refernceCurrency) {
		this.refernceCurrency = refernceCurrency;
	}

	@JsonProperty("roomDetails")
	public List<RoomDetail> getRoomDetails() {
		return roomDetails;
	}

	@JsonProperty("roomDetails")
	public void setRoomDetails(List<RoomDetail> roomDetails) {
		this.roomDetails = roomDetails;
	}

	@JsonProperty("expectedCharges")
	public ExpectedCharges getExpectedCharges() {
		return expectedCharges;
	}

	@JsonProperty("expectedCharges")
	public void setExpectedCharges(ExpectedCharges expectedCharges) {
		this.expectedCharges = expectedCharges;
	}

	@JsonProperty("vipCode")
	public String getVipCode() {
		return vipCode;
	}

	@JsonProperty("vipCode")
	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	@JsonProperty("confirmationId")
	public String getConfirmationId() {
		return confirmationId;
	}

	@JsonProperty("confirmationId")
	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}

	@JsonProperty("flightDetails")
	public FlightDetails getFlightDetails() {
		return flightDetails;
	}

	@JsonProperty("flightDetails")
	public void setFlightDetails(FlightDetails flightDetails) {
		this.flightDetails = flightDetails;
	}

	@JsonProperty("childCount")
	public String getChildCount() {
		return childCount;
	}
	
	public int getChildCounts() {
		return childCount != null ? Integer.parseInt(childCount) : 0;
	}

	@JsonProperty("childCount")
	public void setChildCount(String childCount) {
		this.childCount = childCount;
	}

	@JsonProperty("adultCount")
	public String getAdultCount() {
		return adultCount;
	}

	@JsonProperty("adultCount")
	public void setAdultCount(String adultCount) {
		this.adultCount = adultCount;
	}

	@JsonProperty("primaryGuest")
	public PrimaryGuest getPrimaryGuest() {
		return primaryGuest;
	}

	@JsonProperty("primaryGuest")
	public void setPrimaryGuest(PrimaryGuest primaryGuest) {
		this.primaryGuest = primaryGuest;
	}

	@JsonProperty("emails")
	public List<String> getEmails() {
		return emails;
	}

	@JsonProperty("emails")
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	@JsonProperty("phoneNumber")
	public List<String> getPhoneNumber() {
		return phoneNumber;
	}

	@JsonProperty("phoneNumber")
	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@JsonProperty("nationality")
	public String getNationality() {
		return nationality;
	}

	@JsonProperty("nationality")
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@JsonProperty("reservationStatus")
	public String getReservationStatus() {
		return reservationStatus;
	}

	@JsonProperty("reservationStatus")
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	@JsonProperty("documentToSign")
	private DocumentToSign documentToSign;
	
	public DocumentToSign getDocumentToSign() {
		return documentToSign;
	}

	public void setDocumentToSign(DocumentToSign documentToSign) {
		this.documentToSign = documentToSign;
	}

	public Timespan getTimespan() {
		return timespan;
	}

	public void setTimespan(Timespan timespan) {
		this.timespan = timespan;
	}

	public List<AccompanyGuest> getAccompanyGuests() {
		return accompanyGuests;
	}

	public void setAccompanyGuests(List<AccompanyGuest> accompanyGuests) {
		this.accompanyGuests = accompanyGuests;
	}
	
	
	public List<SharedGuest> getSharedReservations() {
		return sharedReservations;
	}

	public void setSharedReservations(List<SharedGuest> sharedReservations) {
		this.sharedReservations = sharedReservations;
	}

	public String getEmail() {
		return !getEmails().isEmpty() ? getEmails().get(0) : null;
	}
	
	public String getContactNumber() {
		if(getPhoneNumber().isEmpty()) {
			PhoneNumber number= PhoneNumberLibUtils.getNumber(getPhoneNumber().get(0), getPrimaryGuest().getAddress().getCountryCode());
			if(number != null) {
				return String.valueOf(number.getNationalNumber());
			}
		}
		return null;	
	}
}
