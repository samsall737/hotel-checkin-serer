package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomTypeAttributes {

@JsonProperty("roomTypeCode")
private String roomTypeCode;

@JsonProperty("roomTypeCode")
public String getRoomTypeCode() {
return roomTypeCode;
}

@JsonProperty("roomTypeCode")
public void setRoomTypeCode(String roomTypeCode) {
this.roomTypeCode = roomTypeCode;
}

}