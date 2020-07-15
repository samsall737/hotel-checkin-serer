package com.nec.hotels.entity;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "city",
        "state",
        "countryCode",
        "postalCode"
})
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy")})
    protected UUID id;
    
    @JsonProperty("street_address_first")
    private String streetAddressFirst;
    
    @JsonProperty("street_address_second")
    private String streetAddressSecond;
  
    @JsonProperty("street_address_third")
    private String streetAddressThird;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("postal_code")
    private String postalCode;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("state")
    private String state;
    
    @JsonProperty("extn")
    private String extn;
    
    public Address() {}
    
    public Address(String country) {
    	this.country = country;
    }
    
    public Address(String streetAddressFirst, String streetAddressSecond, String streetAddressThird, String city,
			String postalCode, String country, String state, String extn) {
		super();
		this.streetAddressFirst = streetAddressFirst;
		this.streetAddressSecond = streetAddressSecond;
		this.streetAddressThird = streetAddressThird;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.state = state;
		this.extn = extn;
	}

	public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    } 

    public String getStreetAddressFirst() {
		return streetAddressFirst;
	}

	public void setStreetAddressFirst(String streetAddressFirst) {
		this.streetAddressFirst = streetAddressFirst;
	}

	public String getStreetAddressSecond() {
		return streetAddressSecond;
	}

	public void setStreetAddressSecond(String streetAddressSecond) {
		this.streetAddressSecond = streetAddressSecond;
	}

	public String getStreetAddressThird() {
		return streetAddressThird;
	}

	public void setStreetAddressThird(String streetAddressThird) {
		this.streetAddressThird = streetAddressThird;
	}

	public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExtn() {
        return extn;
    }

    public void setExtn(String extn) {
        this.extn = extn;
    }
    
  
}