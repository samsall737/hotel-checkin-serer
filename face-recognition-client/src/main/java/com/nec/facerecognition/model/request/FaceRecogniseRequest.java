
package com.nec.facerecognition.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "reqHeader", "reqData" })
public class FaceRecogniseRequest {

	@JsonProperty("reqHeader")
	private RecogniseReqHeader reqHeader;
	@JsonProperty("reqData")
	private RecogniseReqData reqData;

	@JsonProperty("reqHeader")
	public RecogniseReqHeader getReqHeader() {
		return reqHeader;
	}

	@JsonProperty("reqHeader")
	public void setReqHeader(RecogniseReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	@JsonProperty("reqData")
	public RecogniseReqData getReqData() {
		return reqData;
	}

	@JsonProperty("reqData")
	public void setReqData(RecogniseReqData reqData) {
		this.reqData = reqData;
	}

}
