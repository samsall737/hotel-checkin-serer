package com.nec.hotels.entity;

import java.util.Objects;
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
@Table(name = "special_amenities")
public class SpecialAmenities {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	private UUID id;

	@JsonProperty("amenity")
	@Column(name = "amenity")
	private String amenity;

	@JsonProperty("rate")
	@Column(name = "rate")
	private int rate;

	@JsonProperty("start_date")
	@Column(name = "start_date")
	private long startDate;

	@JsonProperty("end_date")
	@Column(name = "end_date")
	private long endDate;

	@JsonProperty("active")
	@Column(name = "active")
	private boolean active;

	@JsonProperty("currency_code")
	@Column(name = "currency_code")
	private String currencyCode;
	
	@JsonProperty("package_code")
	@Column(name = "package_code")
	private String packageCode;
	
	@Column(name = "quantity")
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("pms_active")
	@Column(name = "pms_active")
	private boolean pmsActive;
	
	@JsonProperty("hotel_id")
	@Column(name = "hotel_id")
	private UUID hotelId;
	
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

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amenity == null) ? 0 : amenity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpecialAmenities other = (SpecialAmenities) obj;
		if (amenity == null) {
			if (other.amenity != null)
				return false;
		} else if (!amenity.equals(other.amenity)) {
			return false;
		}
		return true;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public int getQuantityValue() {
		return !Objects.isNull(quantity) ? quantity : 0  ;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPmsActive() {
		return pmsActive;
	}

	public void setPmsActive(boolean pmsActive) {
		this.pmsActive = pmsActive;
	}

	public UUID getHotelId() {
		return hotelId;
	}

	public void setHotelId(UUID hotelId) {
		this.hotelId = hotelId;
	}
	
	
	
}
