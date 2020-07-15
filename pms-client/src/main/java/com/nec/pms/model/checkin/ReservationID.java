package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationID {

@JsonProperty("uniqueID")
private UniqueID uniqueID;

@JsonProperty("uniqueID")
public UniqueID getUniqueID() {
return uniqueID;
}

@JsonProperty("uniqueID")
public void setUniqueID(UniqueID uniqueID) {
this.uniqueID = uniqueID;
}

}