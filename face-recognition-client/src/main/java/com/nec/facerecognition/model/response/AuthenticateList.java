
package com.nec.facerecognition.model.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nec.facerecognition.model.Registrant;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "no", "registrantID", "faceID", "uniqueID", "verifyScore", "captureImage", "registrant" })
public class AuthenticateList implements Comparable<AuthenticateList> {

	@JsonProperty("no")
	private Integer no;
	@JsonProperty("registrantID")
	private String registrantID;
	@JsonProperty("faceID")
	private String faceID;
	@JsonProperty("uniqueID")
	private String uniqueID;
	@JsonProperty("verifyScore")
	private Integer verifyScore;
	@JsonProperty("captureImage")
	private String captureImage;
	@JsonProperty("registrant")
	private Registrant registrant;
	

	@JsonProperty("no")
	public Integer getNo() {
		return no;
	}

	@JsonProperty("no")
	public void setNo(Integer no) {
		this.no = no;
	}

	@JsonProperty("registrantID")
	public String getRegistrantID() {
		return registrantID;
	}

	@JsonProperty("registrantID")
	public void setRegistrantID(String registrantID) {
		this.registrantID = registrantID;
	}

	@JsonProperty("faceID")
	public String getFaceID() {
		return faceID;
	}

	@JsonProperty("faceID")
	public void setFaceID(String faceID) {
		this.faceID = faceID;
	}

	@JsonProperty("uniqueID")
	public String getUniqueID() {
		return uniqueID;
	}

	@JsonProperty("uniqueID")
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	@JsonProperty("verifyScore")
	public Integer getVerifyScore() {
		return verifyScore;
	}

	@JsonProperty("verifyScore")
	public void setVerifyScore(Integer verifyScore) {
		this.verifyScore = verifyScore;
	}

	@JsonProperty("captureImage")
	public String getCaptureImage() {
		return captureImage;
	}

	@JsonProperty("captureImage")
	public void setCaptureImage(String captureImage) {
		this.captureImage = captureImage;
	}

	@JsonProperty("registrant")
	public Registrant getRegistrant() {
		return registrant;
	}

	@JsonProperty("registrant")
	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registrantID == null) ? 0 : registrantID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticateList other = (AuthenticateList) obj;
		if (registrantID == null) {
			if (other.registrantID != null)
				return false;
		} else if (!registrantID.equals(other.registrantID))
			return false;
		return true;
	}

	@Override
	public int compareTo(AuthenticateList o) {
		return this.registrantID.compareTo(o.registrantID);
	}
}