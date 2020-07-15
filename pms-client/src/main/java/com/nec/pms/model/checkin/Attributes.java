package com.nec.pms.model.checkin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

@JsonProperty("noPost")
private String noPost;
@JsonProperty("doNotMoveRoom")
private String doNotMoveRoom;

@JsonProperty("noPost")
public String getNoPost() {
return noPost;
}

@JsonProperty("noPost")
public void setNoPost(String noPost) {
this.noPost = noPost;
}

@JsonProperty("doNotMoveRoom")
public String getDoNotMoveRoom() {
return doNotMoveRoom;
}

@JsonProperty("doNotMoveRoom")
public void setDoNotMoveRoom(String doNotMoveRoom) {
this.doNotMoveRoom = doNotMoveRoom;
}

}