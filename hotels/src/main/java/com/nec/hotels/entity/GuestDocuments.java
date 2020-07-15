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
@Table(name = "guest_document")
public class GuestDocuments{

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	private UUID id;

	@Column(name = "front_url")
	@JsonProperty("front_url")
	private String frontUrl;
	
	@Column(name = "back_url")
	@JsonProperty("back_url")
	private String backUrl;

	//@NotBlank
	@Column(name = "type")
	private String type;
	
	@Column(name = "document_number")
	private String number;
	
	@JsonProperty("issue_place")
	@Column(name = "issue_place")
	private String issuePlace;
	
	@JsonProperty("issue_date")
	@Column(name = "issue_date")
	private long issueDate;
	
	@JsonProperty("guest_id")
	@Column(name = "guest_id")
	private UUID guestId;
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIssuePlace() {
		return issuePlace;
	}

	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(long issueDate) {
		this.issueDate = issueDate;
	}

	public UUID getGuestId() {
		return guestId;
	}

	public void setGuestId(UUID guestId) {
		this.guestId = guestId;
	}
	
	

}
