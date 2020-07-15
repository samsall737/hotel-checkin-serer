package com.nec.facerecognition.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "resHeader", "resData"})
public class RecogniseRes {

    @JsonProperty("resHeader")
    private RecogniseResHeader resHeader;
    @JsonProperty("resData")
    private RecogniseResData resData;

    @JsonProperty("resHeader")
    public RecogniseResHeader getResHeader() {
        return resHeader;
    }

    @JsonProperty("resHeader")
    public void setResHeader(RecogniseResHeader resHeader) {
        this.resHeader = resHeader;
    }

    @JsonProperty("resData")
    public RecogniseResData getResData() {
        return resData;
    }

    @JsonProperty("resData")
    public void setResData(RecogniseResData resData) {
        this.resData = resData;
    }

}
