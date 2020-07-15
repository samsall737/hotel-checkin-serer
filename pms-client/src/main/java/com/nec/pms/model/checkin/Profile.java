package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

@JsonProperty("customer")
private Customer customer;

@JsonProperty("customer")
public Customer getCustomer() {
return customer;
}

@JsonProperty("customer")
public void setCustomer(Customer customer) {
this.customer = customer;
}

}