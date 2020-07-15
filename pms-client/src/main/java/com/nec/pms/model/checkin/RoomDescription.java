package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDescription {

@JsonProperty("text")
private String text;

@JsonProperty("text")
public String getText() {
return text;
}

@JsonProperty("text")
public void setText(String text) {
this.text = text;
}

}