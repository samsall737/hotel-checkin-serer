package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniqueID {

@JsonProperty("attributes")
private UniqueIDAttributes attributes;
@JsonProperty("value")
private String value;

@JsonProperty("attributes")
public UniqueIDAttributes getAttributes() {
return attributes;
}

@JsonProperty("attributes")
public void setAttributes(UniqueIDAttributes attributes) {
this.attributes = attributes;
}

@JsonProperty("value")
public String getValue() {
return value;
}

@JsonProperty("value")
public void setValue(String value) {
this.value = value;
}

}
