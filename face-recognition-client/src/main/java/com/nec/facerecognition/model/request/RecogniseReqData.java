
package com.nec.facerecognition.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "faceImage", "threshold", "faceFlag", "itemFlag", "targetUniqueIDs", "groupIDs"})
public class RecogniseReqData {

	@JsonProperty("faceImage")
	private String faceImage;
	@JsonProperty("threshold")
	private Integer threshold;
	@JsonProperty("faceFlag")
	private Integer faceFlag;
	@JsonProperty("itemFlag")
	private Integer itemFlag;
	
	@JsonProperty("groupIDs")
	private int[] groupIDs;
	
	@JsonProperty("targetUniqueIDs")
	private List<String> targetUniqueIDs;

	@JsonProperty("faceImage")
	public String getFaceImage() {
		return faceImage;
	}

	@JsonProperty("faceImage")
	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}

	@JsonProperty("threshold")
	public Integer getThreshold() {
		return threshold;
	}

	@JsonProperty("threshold")
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	@JsonProperty("faceFlag")
	public Integer getFaceFlag() {
		return faceFlag;
	}

	@JsonProperty("faceFlag")
	public void setFaceFlag(Integer faceFlag) {
		this.faceFlag = faceFlag;
	}

	@JsonProperty("itemFlag")
	public Integer getItemFlag() {
		return itemFlag;
	}

	@JsonProperty("itemFlag")
	public void setItemFlag(Integer itemFlag) {
		this.itemFlag = itemFlag;
	}

	public List<String> getTargetUniqueIDs() {
		return targetUniqueIDs;
	}

	public void setTargetUniqueIDs(List<String> targetUniqueIDs) {
		this.targetUniqueIDs = targetUniqueIDs;
	}

	public int[] getGroupIDs() {
		return groupIDs;
	}

	public void setGroupIDs(int[] groupIDs) {
		this.groupIDs = groupIDs;
	}
	

}
