package com.nec.hotels.model.guest;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.Guest;

public class GuestDetails {

	@JsonProperty("primary_guest")
	@NotNull(message = "Atleast 1 guest is required")
	private PrimaryGuest primaryGuest;
	
	@JsonProperty("accompany_guests")
	private List<AccompanyGuest> accompanyGuests;
	
	@JsonProperty("sharer_guests")
	private List<SharedGuest> sharerGuests;
	
	@JsonProperty("kids")
	private List<KidsGuest> kids;
	
	@JsonProperty("secondary_guests")
	private List<SecondaryGuest> secondaryGuest;

	public List<SecondaryGuest> getSecondaryGuest() {
		return secondaryGuest;
	}

	public void setSecondaryGuest(List<SecondaryGuest> secondaryGuest) {
		this.secondaryGuest = secondaryGuest;
	}

	public PrimaryGuest getPrimaryGuest() {
		return primaryGuest;
	}

	public void setPrimaryGuest(PrimaryGuest primaryGuest) {
		this.primaryGuest = primaryGuest;
	}

	public List<AccompanyGuest> getAccompanyGuests() {
		return accompanyGuests;
	}

	public void setAccompanyGuests(List<AccompanyGuest> accompanyGuests) {
		this.accompanyGuests = accompanyGuests;
	}

	public List<SharedGuest> getSharerGuests() {
		return sharerGuests;
	}

	public void setSharerGuests(List<SharedGuest> sharerGuests) {
		this.sharerGuests = sharerGuests;
	}

	public List<KidsGuest> getKids() {
		return kids;
	}

	public void setKids(List<KidsGuest> kids) {
		this.kids = kids;
	}
	
}
