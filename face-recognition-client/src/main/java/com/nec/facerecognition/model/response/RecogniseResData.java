
package com.nec.facerecognition.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "authenticateList", "detectFaceData" })
public class RecogniseResData {

	@JsonProperty("authenticateList")
	private List<AuthenticateList> authenticateList = null;
	@JsonProperty("detectFaceData")
	private DetectFaceData detectFaceData;

	@JsonProperty("authenticateList")
	public List<AuthenticateList> getAuthenticateList() {
		return authenticateList;
	}

	@JsonProperty("authenticateList")
	public void setAuthenticateList(List<AuthenticateList> authenticateList) {
		this.authenticateList = authenticateList;
	}

	@JsonProperty("detectFaceData")
	public DetectFaceData getDetectFaceData() {
		return detectFaceData;
	}

	@JsonProperty("detectFaceData")
	public void setDetectFaceData(DetectFaceData detectFaceData) {
		this.detectFaceData = detectFaceData;
	}

}
