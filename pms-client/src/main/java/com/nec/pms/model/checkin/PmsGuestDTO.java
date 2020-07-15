package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PmsGuestDTO {

	@JsonProperty("guestId")
	private String guestId;
	@JsonProperty("email")
	private String email;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("nameTitle")
	private String nameTitle;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("middleName")
	private String middleName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("nationality")
	private String nationality;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("dob")
	private String dob;

	public PmsGuestDTO(String guestId, String email, String phone, String nameTitle, String firstName, String middleName,
					   String lastName, String nationality, String gender, String dob) {
		super();
		this.guestId = guestId;
		this.email = email;
		this.phone = phone;
		this.nameTitle = nameTitle;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.gender = gender;
		this.dob = dob;
	}

	public PmsGuestDTO() {
	}

	@JsonProperty("guestId")
	public String getGuestId() {
		return guestId;
	}

	@JsonProperty("guestId")
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty("nameTitle")
	public String getNameTitle() {
		return nameTitle;
	}

	@JsonProperty("nameTitle")
	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("middleName")
	public String getMiddleName() {
		return middleName;
	}

	@JsonProperty("middleName")
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("nationality")
	public String getNationality() {
		return nationality;
	}

	@JsonProperty("nationality")
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonProperty("dob")
	public String getDob() {
		return dob;
	}

	@JsonProperty("dob")
	public void setDob(String dob) {
		this.dob = dob;
	}

}
