package com.nec.facerecognition.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterFaceDTO {

	private ReqHeader reqHeader = new ReqHeader();
	private ReqData reqData = new ReqData();

	class ReqHeader {
		private String reqDatetime;

		public String getReqDatetime() {
			return reqDatetime;
		}

		public void setReqDatetime(String reqDatetime) {
			this.reqDatetime = reqDatetime;
		}
	}

	class ReqData {
		private String uniqueID;
		private String name1;
		private String captureImage;
		private String memo;
		private int judgmentScore;
		private int[]groupIDs;
 
		public String getUniqueID() {
			return uniqueID;
		}

		public void setUniqueID(String uniqueID) {
			this.uniqueID = uniqueID;
		}

		public String getName1() {
			return name1;
		}

		public void setName1(String name1) {
			this.name1 = name1;
		}

		public String getCaptureImage() {
			return captureImage;
		}

		public void setCaptureImage(String captureImage) {
			this.captureImage = captureImage;
		}

		public int getJudgmentScore() {
			return judgmentScore;
		}

		public void setJudgmentScore(int judgmentScore) {
			this.judgmentScore = judgmentScore;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public int[] getGroupIDs() {
			return groupIDs;
		}

		public void setGroupIDs(int[] groupIDs) {
			this.groupIDs = groupIDs;
		}
		
		
	}

	public ReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(String reqDateTime) {
		this.reqHeader.setReqDatetime(reqDateTime);
	}

	public ReqData getReqData() {
		return reqData;
	}

	
	
	public void setReqData(String uniqueID, String name1, String captureImage, String memo, int judgmentScore, int[] groupIds) {
		this.reqData.setUniqueID(uniqueID);
		this.reqData.setName1(name1);
		this.reqData.setCaptureImage(captureImage);
		this.reqData.setMemo(memo);
		this.reqData.setJudgmentScore(judgmentScore);
		this.reqData.setGroupIDs(groupIds);
	}
}
