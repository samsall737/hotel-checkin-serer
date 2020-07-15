package com.nec.hotels.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "guest_special_amenities")
public class GuestSpecialAmenities {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	@Column(name = "id")
	@JsonProperty("id")
	private UUID id;

	@Column(name = "amenity")
	@JsonProperty("amenity")
	private String amenity;
	
	@Column(name = "rate")
	@JsonProperty("rate")
	private int rate;
	
	@Column(name = "quantity")
	@JsonProperty("quantity")
	private int quantity;
	
	@Column(name = "package_code")
	@JsonProperty("package_code")
	private String packageCode;
	
	@Column(name = "start_date")
	@JsonProperty("start_date")
	private String startDate;
	
	@Column(name = "end_date")
	@JsonProperty("end_date")
	private String endDate;

	@Column(name = "booking_id")
	@JsonProperty("booking_id")
	private String bookingId;
	
	@JsonProperty("special_amenities_id")
	@Column(name = "special_amenities_id")
	private UUID specialAmenitiesId;
	
	public GuestSpecialAmenities() {
		this.id = null;
		this.amenity = null;
		this.rate = 0;
	}

	
	public GuestSpecialAmenities(String amenity, int rate, String packageCode, String startDate,
			String endDate, int quantity, String bookingId, UUID specialAmenitiesId) {
		super();
		this.amenity = amenity;
		this.rate = rate;
		this.packageCode = packageCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.quantity = quantity;
		this.bookingId = bookingId;
		this.specialAmenitiesId = specialAmenitiesId;
	}

	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAmenity() {
		return amenity;
	}

	public void setAmenity(String amenity) {
		this.amenity = amenity;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}


	public String getPackageCode() {
		return packageCode;
	}


	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getBookingId() {
		return bookingId;
	}


	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	public UUID getSpecialAmenitiesId() {
		return specialAmenitiesId;
	}


	public void setSpecialAmenitiesId(UUID specialAmenitiesId) {
		this.specialAmenitiesId = specialAmenitiesId;
	}
	
	
}
