package com.nec.hotels.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.Title;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Guest {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
	protected UUID id;

	@JsonProperty("first_name")
	@Column(name = "first_name")
	protected String firstName;

	@JsonProperty("last_name")
	@Column(name = "last_name")
	protected String lastName;

	@Column(name = "gender")
	protected Title title;

	@Column(name = "nationality")
	protected String nationality;

	@Column(name = "is_vip")
	@JsonProperty("is_vip")
	protected boolean isVIP;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "uploads_id")
	@JsonProperty("guest_uploads")
	protected GuestDocuments guestDocuments;  //docs....Idproofs  vg: done

	@JsonProperty("date_of_birth")
	@Column(name = "date_of_birth")
	protected Long dateOfBirth;

	@JsonProperty("document_name_validation")
	@Column(name = "document_name_validation")
	protected Integer documentNameValidation;

	@JsonProperty("document_image_validation")
	@Column(name = "document_image_validation")
	protected Integer documentImageValidation;

	@JsonIgnore
	@Column(name = "pms_guest_id")
	protected String pmsGuestId;
	
	@JsonProperty("is_frro")
	@Column(name = "is_frro")
	protected boolean isFrro = false;
	
	@JsonIgnore
	@Column(name = "guest_type")
	protected GuestType type;
	
	@Column(name = "isd_code")
	@JsonProperty("isd_code")
	private int isdCode;

	@Digits(integer = 16, fraction = 0)
	@JsonProperty("contact_number")
	private String contactNumber;

	@Column(name = "email_id")
	@JsonProperty("email_id")
	private String email;

	
	@JsonProperty("address")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@Column(name = "signature_url")
	@JsonProperty("signature_url")
	private String signatureUrl;
	
	@Column(name = "regcard_url")
	@JsonProperty("regcard_url")
	private String regcardUrl;
	
	@Column(name = "image_url")
	@JsonProperty("image_url")
	private String imageUrl;
	
	@Column(name = "booking_id")
	@JsonProperty("booking_id")
	private String bookingId;

	
	//primaryGuest
	public Guest(String firstName, String lastName, Title title, String nationality, Address address,
			String contactNumber, String email, GuestType type, String bookingId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.nationality = nationality;
		this.address = address;
		this.contactNumber = contactNumber;
		this.email = email;
		this.type = type;
		this.bookingId = bookingId;
	}
	
	//Secondary Guest
	public Guest(String pmsGuestId, Title title, String firstName, String lastName,String nationality, 
			Long dob, GuestType type, String bookingId) {
				this.pmsGuestId = pmsGuestId;
				this.title = title;
				this.firstName = firstName;
				this.lastName = lastName;
				this.nationality = nationality;
				this.dateOfBirth = dob;
				this.type = type;
				this.bookingId = bookingId;
			}
	
	

	public static class Builder {
		private UUID id;
		private String firstName;
		private String lastName;
		private Title title;
		private String nationality;
		private boolean isVIP;
		private GuestDocuments guestDocuments;
		private Long dateOfBirth;
		private Integer documentNameValidation;
		private Integer documentImageValidation;
		private String pmsGuestId;
		private int isdCode;
		private String contactNumber;
		private String email;
		private Address address;
		private GuestType type;
		private boolean isFRRO;
		private String bookingId;
		
		public Builder() {}
		
		
		public Builder(String firstName, String lastName, String title, String nationality, String bookingId) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.title = Title.valueOf(title.toUpperCase().substring(0, title.lastIndexOf('.')));
			this.nationality = nationality;
			this.bookingId = bookingId;
		}
		
		//Primary Guest
		public Guest createPrimaryGuest(String firstName, String lastName, String title, String nationality, Address address,
				String contactNumber, String email, GuestType type, String bookingId) {
			return new Guest(firstName, lastName, Title.valueOf(title.toUpperCase().substring(0, title.lastIndexOf('.'))), nationality,
					address, contactNumber, email, type, bookingId);
		}
		
		
		//Secondary Guest
		public Guest createSecondaryGuest(String pmsGuestId, String title, String firstName, String lastName,String nationality, long dob, GuestType type, String bookingId) {
			return new Guest(pmsGuestId, Title.valueOf(title.toUpperCase().substring(0, title.lastIndexOf('.'))), firstName,
					lastName, nationality, dob, type, bookingId);
		}
		

		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}

		public Builder setVIP(boolean isVIP) {
			this.isVIP = isVIP;
			return this;
		}

		public Builder setGuestUploads(GuestDocuments guestDocuments) {
			this.guestDocuments = guestDocuments;
			return this;
		}

		public Builder setDateOfBirth(Long dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder setDocumentNameValidation(Integer documentNameValidation) {
			this.documentNameValidation = documentNameValidation;
			return this;
		}

		public Builder setDocumentImageValidation(Integer documentImageValidation) {
			this.documentImageValidation = documentImageValidation;
			return this;
		}

		public Builder setPmsGuestId(String pmsGuestId) {
			this.pmsGuestId = pmsGuestId;
			return this;
		}
		
		public Builder setIsdCode(int isdCode) {
			this.isdCode = isdCode;
			return this;
		}
		
		public Builder setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
			return this;
		}
		
		public Builder setEmailId(String emailId) {
			this.email = emailId;
			return this;
		}
		
		public Builder setGuestType(GuestType type) {
			this.type = type;
			return this;
		}
		
		public Builder setFRRO(boolean isFRRO) {
			this.isFRRO = isFRRO;
			return this;
		}
		
		public Builder setBookingId(String bookingId) {
			this.bookingId = bookingId;
			return this;
		}
		

		public Guest build() {
			return new Guest(this);
		}
	}

	public Guest() {
	}
	
	public Guest(GuestType type) {
		this.type =  type;
	}

	//create two constructor for primary and secondary
	public Guest(Builder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.title = builder.title;
		this.nationality = builder.nationality;
		this.isVIP = builder.isVIP;
		this.guestDocuments = builder.guestDocuments;
		this.dateOfBirth = builder.dateOfBirth;
		this.documentNameValidation = builder.documentNameValidation;
		this.documentImageValidation = builder.documentImageValidation;
		this.pmsGuestId = builder.pmsGuestId;
		this.isdCode = builder.isdCode;
		this.contactNumber = builder.contactNumber;
		this.email = builder.email;
		this.type = builder.type;
		this.isFrro = builder.isFRRO;
		this.address = builder.address;
		this.bookingId = builder.bookingId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@JsonIgnore
	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	public Long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title.contains("."))
		this.title = Title.valueOf(title.toUpperCase().substring(0, title.lastIndexOf('.')));
		else
		this.title = Title.valueOf(title.toUpperCase());
			
	}

	@JsonProperty("is_vip")
	public boolean getisVIP() {
		return isVIP;
	}

	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}

	public GuestDocuments getGuestUploads() {
		return guestDocuments;
	}

	public void setGuestUploads(GuestDocuments guestDocuments) {
		this.guestDocuments = guestDocuments;
	}

	public Integer getDocumentNameValidation() {
		return documentNameValidation;
	}

	public void setDocumentNameValidation(int documentNameValidation) {
		this.documentNameValidation = documentNameValidation;
	}

	public Integer getDocumentImageValidation() {
		return documentImageValidation;
	}

	public void setDocumentImageValidation(int documentImageValidation) {
		this.documentImageValidation = documentImageValidation;
	}

	public String getPmsGuestId() {
		return pmsGuestId;
	}

	public void setPmsGuestId(String pmsGuestId) {
		this.pmsGuestId = pmsGuestId;
	}

	public GuestDocuments getGuestDocuments() {
		return guestDocuments;
	}

	public void setGuestDocuments(GuestDocuments guestDocuments) {
		this.guestDocuments = guestDocuments;
	}

	public boolean isFRRO() {
		return isFrro;
	}

	public void setFRRO(boolean isFRRO) {
		this.isFrro = isFRRO;
	}

	public GuestType getType() {
		return type;
	}

	public void setType(GuestType type) {
		this.type = type;
	}

	public int getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(int isdCode) {
		this.isdCode = isdCode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDocumentNameValidation(Integer documentNameValidation) {
		this.documentNameValidation = documentNameValidation;
	}

	public void setDocumentImageValidation(Integer documentImageValidation) {
		this.documentImageValidation = documentImageValidation;
	}

	public String getSignatureUrl() {
		return signatureUrl;
	}

	public void setSignatureUrl(String signatureUrl) {
		this.signatureUrl = signatureUrl;
	}

	public String getRegcardUrl() {
		return regcardUrl;
	}

	public void setRegcardUrl(String regcardUrl) {
		this.regcardUrl = regcardUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	


}
