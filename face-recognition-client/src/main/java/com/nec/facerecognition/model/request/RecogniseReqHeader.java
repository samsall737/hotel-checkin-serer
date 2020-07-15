
package com.nec.facerecognition.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "reqDatetime"
})
public class RecogniseReqHeader {

    @JsonProperty("reqDatetime")
    private String reqDatetime;

    @JsonProperty("reqDatetime")
    public String getReqDatetime() {
        return reqDatetime;
    }

    @JsonProperty("reqDatetime")
    public void setReqDatetime(String reqDatetime) {
        this.reqDatetime = reqDatetime;
    }

}
