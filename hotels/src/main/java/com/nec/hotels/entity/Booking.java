package com.nec.hotels.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.exception.HotelCheckinException;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.model.booking.FlightDetails;
import com.nec.hotels.model.booking.Room;
import com.nec.hotels.model.booking.RoomInclusions;
import com.nec.pms.model.reservation.Data;

@Entity
@Table(name = "booking")
@JsonInclude(Include.NON_NULL)
public class Booking {

	@Id
	@Column(name = "id")
	//@GeneratedValue(generator = "UUID")
	//@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
		//	@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	protected String id;
	
	@Column(name = "arrival_time")
	@JsonProperty("arrival_time")
	private Long arrivalTime;

	@Column(name = "departure_time")
	@JsonProperty("departure_date")
	private Long departureTime;

	@Column(name = "checkin_time")
	@JsonProperty("check_in_time")
	private Long checkInTime;

	@Column(name = "pre_checkin_status")
	@JsonProperty("pre_check_in_status")
	private PreCheckinStatus preCheckinStatus;

	@Column(name = "checkin_status")
	@JsonProperty("check_in_status")
	private CheckinStatus checkinStatus;
	
	@Column(name = "booking_status")
	@JsonProperty("booking_status")
	private String bookingStatus;


	@OneToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
//	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "card_id", referencedColumnName = "id")
	@JsonProperty("credit_card")
	private CardDetails cardDetails;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name="booking_id")
	private List<Guest> guests;

	@Column(name = "face_recognition_guest_id")
	@JsonProperty("face_recognition_guest_id")
	private UUID faceRecognitionGuestId;

	@JsonProperty("pay_at_desk")
	@Column(name = "pay_at_desk")
	private boolean payAtDesk;
	
	@JsonProperty("no_of_kids")
	@Column(name = "no_of_kids")
	private int noOfKids;

	@JsonProperty("hotel_id")
	@Column(name = "hotel_id")
	private UUID hotelId;

	@Column(name = "confirmation_number")
	@JsonProperty("confirmation_number")
	private String confirmationNumber;

	@JsonProperty("steps_completed")
	@Column(name = "steps_completed")
	private int stepsCompleted;
	
	@JsonProperty("special_amenities")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="booking_id")
	private List<GuestSpecialAmenities> specialAmenities;

	@JsonProperty("special_remarks")
	@Column(name = "special_remarks")
	private String specialRemarks;
	
	@JsonIgnore
	@Column(name = "detail_fetched")
	private boolean detailFetched;
	
	@Transient
	private List<Room> room;
	
	@Transient
	@JsonProperty("room_inclusion")
	private List<RoomInclusions> roomInclusions;
	

	//@Transient
	@OneToOne(cascade=CascadeType.ALL)
	@JsonProperty("flight_details")
	@JoinColumn(name = "flight_details_id", referencedColumnName = "id")
	private FlightDetails flightDetails;

	public static class Builder {
		private String id;
		private Long arrivalTime;
		private Long departureTime;
	//	private final String timeZone;
		private Long checkInTime;
		private PreCheckinStatus preCheckinStatus;
		private CheckinStatus checkinStatus;
		private CardDetails cardDetails;
		private List<Guest> guests;
		private UUID faceRecognitionGuestId;
		private boolean payAtDesk;
		private int stepsCompleted;
		private String bookingStatus;
		private List<GuestSpecialAmenities> specialAmenities;
		private String specialRemarks;
		private String confirmationNumber;
		private int noOfKids;
		private UUID hotelId;
		
		public Builder() {
        }
		

		public Builder(String id, Long arrivalTime, Long departureTime,
				PreCheckinStatus preCheckinStatus, CheckinStatus checkinStatus,
				List<Guest> guests,String confirmationNumber, int noOfKids, UUID hotelId) {
			this.id = id;
			this.arrivalTime = arrivalTime;
			this.departureTime = departureTime;
		//	this.timeZone = timeZone;
			this.preCheckinStatus = preCheckinStatus;
			this.checkinStatus = checkinStatus;
			this.guests = guests;
			this.confirmationNumber = confirmationNumber;
			this.noOfKids = noOfKids;
			this.hotelId = hotelId;
		}
		
		public Builder setId(UUID id) {
			this.id = id.toString();
			return this;
		}
	

		
		public Builder setConfirmationNumber(String confirmationNumber) {
			this.confirmationNumber = confirmationNumber;
			return this;
		}

		public Builder setCheckInTime(Long checkInTime) {
			this.checkInTime = checkInTime;
			return this;
		}

		public Builder setBookingStatus(String bookingStatus ) {
			this.bookingStatus = bookingStatus;
			return this;
		}
		
		public Builder setCreditCard(CardDetails cardDetails) {
			this.cardDetails = cardDetails;
			return this;
		}

		public Builder setFaceRecognitionGuestId(UUID faceRecognitionGuestId) {
			this.faceRecognitionGuestId = faceRecognitionGuestId;
			return this;
		}

		public Builder setPayAtDesk(boolean payAtDesk) {
			this.payAtDesk = payAtDesk;
			return this;
		}

		public Builder setStepsCompleted(int stepsCompleted) {
			this.stepsCompleted = stepsCompleted;
			return this;
		}
		
		public Builder setGuestSpecialAmenities(List<GuestSpecialAmenities> specialAmenities) {
			this.specialAmenities = specialAmenities;
			return this;
		}
		
		public Builder setSpecialRemarks(String specialRemarks) {
			this.specialRemarks = specialRemarks;
			return this;
		}
		
		public Builder setNoOfKids(int noOfKids) {
			this.noOfKids = noOfKids;
			return this;
		}
		
		public Builder setHotelId(UUID hotelId) {
			this.hotelId = hotelId;
			return this;
		}

		public Booking build() {
			return new Booking(this);
		}
	}

	public Booking(Builder builder) {
		this.id = builder.id;
		this.arrivalTime = builder.arrivalTime;
		this.departureTime = builder.departureTime;
		//this.timeZone = builder.timeZone;
		this.checkInTime = builder.checkInTime;
		this.preCheckinStatus = builder.preCheckinStatus;
		this.checkinStatus = builder.checkinStatus;
		this.guests = builder.guests;
		this.cardDetails = builder.cardDetails;
		this.faceRecognitionGuestId = builder.faceRecognitionGuestId;
		this.payAtDesk = builder.payAtDesk;
		this.stepsCompleted = builder.stepsCompleted;
		this.specialAmenities = builder.specialAmenities;
		this.specialRemarks = builder.specialRemarks;
		this.confirmationNumber=builder.confirmationNumber;
		this.noOfKids = builder.noOfKids;
		this.hotelId = hotelId;
	}

	public Booking() {
	}

	public CardDetails getCreditCard() {
		return cardDetails;
	}

	public void setCreditCard(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}

	public Long getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Long checkInTime) {
		this.checkInTime = checkInTime;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public int getNoOfKids() {
		return noOfKids;
	}

	public void setNoOfKids(int noOfKids) {
		this.noOfKids = noOfKids;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public CheckinStatus getCheckinStatus() {
		return checkinStatus;
	}


	/*public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}*/

	public PreCheckinStatus getPreCheckinStatus() {
		return preCheckinStatus;
	}

	public void setPreCheckinStatus(String preCheckinStatus) {
		switch (preCheckinStatus.toUpperCase()) {
		case "SUCCESS":
			this.preCheckinStatus = PreCheckinStatus.SUCCESS;
			break;
		case "FAILURE":
			this.preCheckinStatus = PreCheckinStatus.FAILURE;
			break;
		case "ATTEMPTED":
			this.preCheckinStatus = PreCheckinStatus.ATTEMPTED;
			break;
		case "PENDING":
			this.preCheckinStatus = PreCheckinStatus.PENDING;
			break;
		default:
			throw new HotelCheckinException("Invalid Pre-Checkin Status", HttpStatus.BAD_REQUEST);
		}
	}

	public UUID getFaceRecognitionGuestId() {
		return faceRecognitionGuestId;
	}

	public void setFaceRecognitionGuestId(UUID faceRecognitionGuestId) {
		this.faceRecognitionGuestId = faceRecognitionGuestId;
	}

	public void setCheckinStatus(String checkinStatus) {
		switch (checkinStatus.toUpperCase()) {
		case "SUCCESS":
			this.checkinStatus = CheckinStatus.SUCCESS;
			break;
		case "FAILURE":
			this.checkinStatus = CheckinStatus.FAILURE;
			break;
		case "PENDING":
			this.checkinStatus = CheckinStatus.PENDING;
			break;
		default:
			throw new HotelCheckinException("Invalid Checkin Status", HttpStatus.BAD_REQUEST);
		}
	}

	public void setPreCheckinStatus(PreCheckinStatus preCheckinStatus) {
		this.preCheckinStatus = preCheckinStatus;
	}

	public void setCheckinStatus(CheckinStatus checkinStatus) {
		this.checkinStatus = checkinStatus;
	}


	public boolean isPayAtDesk() {
		return payAtDesk;
	}

	public void setPayAtDesk(boolean payAtDesk) {
		this.payAtDesk = payAtDesk;
	}


	public int getStepsCompleted() {
		return stepsCompleted;
	}

	public void setStepsCompleted(int stepsCompleted) {
		this.stepsCompleted = stepsCompleted;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
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

	public boolean getDetailFetched() {
		return detailFetched;
	}

	public void setDetailFetched(boolean detailFetched) {
		this.detailFetched = detailFetched;
	}	

	public List<Room> getRoom() {
		return room;
	}

	public void setRoom(List<Room> room) {
		this.room = room;
	}
	

	public List<RoomInclusions> getRoomInclusions() {
		if(roomInclusions == null) {
			roomInclusions = new ArrayList<>();
		}
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
	
	private void setFlightDetails(Data pmsData) {
		com.nec.pms.model.FlightDetails flightDetail = pmsData.getFlightDetails();
		if(flightDetails != null) {	
			setFlightDetails(new FlightDetails("", flightDetail.getArrivalTransport().getTime(), flightDetail.getDepartureTransport().getTime(),
					flightDetail.getArrivalTransport().getTransportationRequired(), flightDetail.getDepartureTransport().getTransportationRequired()));
		}
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}

	public boolean isDetailFetched() {
		return detailFetched;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public UUID getHotelId() {
		return hotelId;
	}

	public void setHotelId(UUID hotelId) {
		this.hotelId = hotelId;
	}
	
	
}