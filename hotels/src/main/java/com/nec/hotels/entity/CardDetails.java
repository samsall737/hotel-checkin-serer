package com.nec.hotels.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.exception.HotelCheckinException;

@Entity
@Table(name = "card_details")
public class CardDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	private UUID id;

	@Column(name = "number")
	@NotNull(message = "Credit Card Number missing")
	private long number;

	@Column(name = "holder_name")
	@NotBlank(message = "Name Cannot be Empty")
	@JsonProperty("holder_name")
	private String holderName;

	@NotNull(message = "Expiry Date missing")
	@Column(name = "expiry")
	@JsonProperty("expiry_date")
	private Date expiryDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getExpiryDate() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.expiryDate);
	}

	
	public void setExpiryDate(String expiryDate) {
		if (Integer.parseInt(expiryDate.substring(5, expiryDate.lastIndexOf('-'))) > 12) {
			throw new HotelCheckinException("Month Cannot be Greater than 12", HttpStatus.BAD_REQUEST);
		}
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.expiryDate = formatter.parse(expiryDate);
		} catch (ParseException e) {
			LoggerFactory.getLogger(CardDetails.class).error("parsing Exception", e.getMessage());
		}
	}

}
