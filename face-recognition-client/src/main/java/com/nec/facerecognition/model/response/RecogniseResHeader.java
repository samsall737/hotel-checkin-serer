
package com.nec.facerecognition.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "resDatetime",
    "errorCode",
    "errorMsg"
})
public class RecogniseResHeader {

    @JsonProperty("resDatetime")
    private String resDatetime;
    @JsonProperty("errorCode")
    private Integer errorCode;
    @JsonProperty("errorMsg")
    private String errorMsg;

    @JsonProperty("resDatetime")
    public String getResDatetime() {
        return resDatetime;
    }

    @JsonProperty("resDatetime")
    public void setResDatetime(String resDatetime) {
        this.resDatetime = resDatetime;
    }

    @JsonProperty("errorCode")
    public Integer getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("errorMsg")
    public String getErrorMsg() {
        return errorMsg;
    }

    @JsonProperty("errorMsg")
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
