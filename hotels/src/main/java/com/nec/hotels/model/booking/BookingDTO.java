package com.nec.hotels.model.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.CardDetails;
import com.nec.hotels.entity.GuestSpecialAmenities;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.model.guest.AccompanyGuest;
import com.nec.hotels.model.guest.KidsGuest;
import com.nec.hotels.model.guest.PrimaryGuest;
import com.nec.hotels.model.guest.SharedGuest;

public class BookingDTO {
	
	private String id;

	@JsonProperty("arrival_time")
	private Long arrivalTime;

	@JsonProperty("departure_time")
	private Long departureTime;

	@JsonProperty("check_in_time")
	private Long checkInTime;

	@JsonProperty("pre_check_in_status")
	private PreCheckinStatus preCheckinStatus;

	@JsonProperty("check_in_status")
	private CheckinStatus checkinStatus;

	@JsonProperty("booking_status")
	private String bookingStatus;

	@JsonProperty("card_details")
	private CardDetails creditCard;

	@JsonProperty("face_recognition_guest_id")
	private UUID faceRecognitionGuestId;

	@JsonProperty("pay_at_desk")
	private boolean payAtDesk;

	@JsonProperty("confirmation_number")
	private String confirmationNumber;

	@JsonProperty("steps_completed")
	private int stepsCompleted;

	@JsonProperty("special_amenities")
	private List<GuestSpecialAmenities> specialAmenities;

	@JsonProperty("special_remarks")
	private String specialRemarks;

	@JsonIgnore
	@JsonProperty("detail_fetched")
	private boolean detailFetched;

	private List<Room> room;

	@JsonProperty("room_inclusion")
	private List<RoomInclusions> roomInclusions;

	@JsonProperty("flight_details")
	private FlightDetails flightDetails;

	@JsonProperty("primary_guest")
	private PrimaryGuest primaryGuest;

	@JsonProperty("accompany_guests")
	private List<AccompanyGuest> accompanyGuests;

	@JsonProperty("shared_guests")
	private List<SharedGuest> sharerGuests;

	@JsonProperty("kids")
	private List<KidsGuest> kids;
	
	@JsonProperty("no_of_kids")
	private int noOfKids;
	

	public BookingDTO(String id, Long arrivalTime, Long departureTime, Long checkInTime, PreCheckinStatus preCheckinStatus, CheckinStatus checkinStatus, String bookingStatus, CardDetails creditCard, UUID faceRecognitionGuestId, boolean payAtDesk, String confirmationNumber, int stepsCompleted,
			List<GuestSpecialAmenities> specialAmenities, String specialRemarks, boolean detailFetched, List<Room> room, List<RoomInclusions> roomInclusions, FlightDetails flightDetails, int noOfKids) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.checkInTime = checkInTime;
		this.preCheckinStatus = preCheckinStatus;
		this.checkinStatus = checkinStatus;
		this.bookingStatus = bookingStatus;
		this.creditCard = creditCard;
		this.faceRecognitionGuestId = faceRecognitionGuestId;
		this.payAtDesk = payAtDesk;
		this.confirmationNumber = confirmationNumber;
		this.stepsCompleted = stepsCompleted;
		this.specialAmenities = specialAmenities;
		this.specialRemarks = specialRemarks;
		this.detailFetched = detailFetched;
		this.room = room;
		this.roomInclusions = roomInclusions;
		this.flightDetails = flightDetails;
		this.noOfKids = noOfKids;
	}
	
	public BookingDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}

	public Long getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Long checkInTime) {
		this.checkInTime = checkInTime;
	}

	public PreCheckinStatus getPreCheckinStatus() {
		return preCheckinStatus;
	}

	public void setPreCheckinStatus(PreCheckinStatus preCheckinStatus) {
		this.preCheckinStatus = preCheckinStatus;
	}

	public CheckinStatus getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(CheckinStatus checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public CardDetails getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CardDetails creditCard) {
		this.creditCard = creditCard;
	}
	public UUID getFaceRecognitionGuestId() {
		return faceRecognitionGuestId;
	}

	public void setFaceRecognitionGuestId(UUID faceRecognitionGuestId) {
		this.faceRecognitionGuestId = faceRecognitionGuestId;
	}

	public boolean isPayAtDesk() {
		return payAtDesk;
	}

	public void setPayAtDesk(boolean payAtDesk) {
		this.payAtDesk = payAtDesk;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public int getStepsCompleted() {
		return stepsCompleted;
	}

	public void setStepsCompleted(int stepsCompleted) {
		this.stepsCompleted = stepsCompleted;
	}

	public List<GuestSpecialAmenities> getSpecialAmenities() {
		return specialAmenities;
	}

	public void setSpecialAmenities(List<GuestSpecialAmenities> specialAmenities) {
		this.specialAmenities = specialAmenities;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	@JsonIgnore
	public boolean isDetailFetched() {
		return detailFetched;
	}

	public void setDetailFetched(boolean detailFetched) {
		this.detailFetched = detailFetched;
	}

	public List<Room> getRoom() {
		if(room == null) {
			return new ArrayList<>();
		}
		return room;
	}

	public void setRoom(List<Room> room) {
		this.room = room;
	}

	public List<RoomInclusions> getRoomInclusions() {
		return roomInclusions;
	}

	public void setRoomInclusions(List<RoomInclusions> roomInclusions) {
		this.roomInclusions = roomInclusions;
	}

	public FlightDetails getFlightDetails() {
		return flightDetails;
	}

	public void setFlightDetails(FlightDetails flightDetails) {
		this.flightDetails = flightDetails;
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

	public int getNoOfKids() {
		return noOfKids;
	}

	public void setNoOfKids(int noOfKids) {
		this.noOfKids = noOfKids;
	}
	
	

}