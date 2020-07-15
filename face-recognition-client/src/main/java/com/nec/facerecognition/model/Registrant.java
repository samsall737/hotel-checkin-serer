
package com.nec.facerecognition.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "uniqueID",
    "name1",
    "name2",
    "yomi1",
    "yomi2",
    "birthday",
    "nationality",
    "postcode",
    "prefecture",
    "address1",
    "address2",
    "phone1",
    "phone2",
    "email1",
    "email2",
    "company",
    "department",
    "employeeCode",
    "colorCode",
    "effectiveFrom",
    "effectiveTo",
    "title",
    "bodyFeature",
    "regReason",
    "groupIDs",
    "memo",
    "protectFlg",
    "judgmentScore",
    "faceID"
})
public class Registrant {

    @JsonProperty("uniqueID")
    private String uniqueID;
    @JsonProperty("name1")
    private String name1;
    @JsonProperty("name2")
    private String name2;
    @JsonProperty("yomi1")
    private String yomi1;
    @JsonProperty("yomi2")
    private String yomi2;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("prefecture")
    private String prefecture;
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("phone1")
    private int phone1;
    @JsonProperty("phone2")
    private int phone2;
    @JsonProperty("email1")
    private String email1;
    @JsonProperty("email2")
    private String email2;
    @JsonProperty("company")
    private String company;
    @JsonProperty("department")
    private String department;
    @JsonProperty("employeeCode")
    private int employeeCode;
    @JsonProperty("colorCode")
    private int colorCode;
    @JsonProperty("effectiveFrom")
    private String effectiveFrom;
    @JsonProperty("effectiveTo")
    private String effectiveTo;
    @JsonProperty("title")
    private Integer gender;
    @JsonProperty("bodyFeature")
    private String bodyFeature;
    @JsonProperty("regReason")
    private String regReason;
    @JsonProperty("groupIDs")
    private ArrayList<Integer> groupIDs;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("protectFlg")
    private Integer protectFlg;
    @JsonProperty("judgmentScore")
    private Integer judgmentScore;
    @JsonProperty("faceID")
    private String faceID;
    


    @JsonProperty("uniqueID")
    public String getUniqueID() {
        return uniqueID;
    }

    @JsonProperty("uniqueID")
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    @JsonProperty("name1")
    public String getName1() {
        return name1;
    }

    @JsonProperty("name1")
    public void setName1(String name1) {
        this.name1 = name1;
    }

    @JsonProperty("name2")
    public String getName2() {
        return name2;
    }

    @JsonProperty("name2")
    public void setName2(String name2) {
        this.name2 = name2;
    }

    @JsonProperty("yomi1")
    public String getYomi1() {
        return yomi1;
    }

    @JsonProperty("yomi1")
    public void setYomi1(String yomi1) {
        this.yomi1 = yomi1;
    }

    @JsonProperty("yomi2")
    public String getYomi2() {
        return yomi2;
    }

    @JsonProperty("yomi2")
    public void setYomi2(String yomi2) {
        this.yomi2 = yomi2;
    }

    @JsonProperty("birthday")
    public String getBirthday() {
        return birthday;
    }

    @JsonProperty("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @JsonProperty("nationality")
    public String getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @JsonProperty("prefecture")
    public String getPrefecture() {
        return prefecture;
    }

    @JsonProperty("prefecture")
    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    @JsonProperty("address1")
    public String getAddress1() {
        return address1;
    }

    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @JsonProperty("address2")
    public String getAddress2() {
        return address2;
    }

    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @JsonProperty("phone1")
    public int getPhone1() {
        return phone1;
    }

    @JsonProperty("phone1")
    public void setPhone1(int phone1) {
        this.phone1 = phone1;
    }

    @JsonProperty("phone2")
    public int getPhone2() {
        return phone2;
    }

    @JsonProperty("phone2")
    public void setPhone2(int phone2) {
        this.phone2 = phone2;
    }

    @JsonProperty("email1")
    public String getEmail1() {
        return email1;
    }

    @JsonProperty("email1")
    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    @JsonProperty("email2")
    public String getEmail2() {
        return email2;
    }

    @JsonProperty("email2")
    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(String company) {
        this.company = company;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonProperty("employeeCode")
    public int getEmployeeCode() {
        return employeeCode;
    }

    @JsonProperty("employeeCode")
    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    @JsonProperty("colorCode")
    public int getColorCode() {
        return colorCode;
    }

    @JsonProperty("colorCode")
    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @JsonProperty("effectiveFrom")
    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    @JsonProperty("effectiveFrom")
    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    @JsonProperty("effectiveTo")
    public String getEffectiveTo() {
        return effectiveTo;
    }

    @JsonProperty("effectiveTo")
    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @JsonProperty("title")
    public Integer getGender() {
        return gender;
    }

    @JsonProperty("title")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonProperty("bodyFeature")
    public String getBodyFeature() {
        return bodyFeature;
    }

    @JsonProperty("bodyFeature")
    public void setBodyFeature(String bodyFeature) {
        this.bodyFeature = bodyFeature;
    }

    @JsonProperty("regReason")
    public String getRegReason() {
        return regReason;
    }

    @JsonProperty("regReason")
    public void setRegReason(String regReason) {
        this.regReason = regReason;
    }

  
    public ArrayList<Integer> getGroupIDs() {
    	if(Objects.isNull(groupIDs)) {
    		return new ArrayList<Integer>();
    	}
		return groupIDs;
	}

	public void setGroupIDs(ArrayList<Integer> groupIDs) {
		this.groupIDs = groupIDs;
	}

	@JsonProperty("memo")
    public String getMemo() {
        return memo;
    }

    @JsonProperty("memo")
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @JsonProperty("protectFlg")
    public Integer getProtectFlg() {
        return protectFlg;
    }

    @JsonProperty("protectFlg")
    public void setProtectFlg(Integer protectFlg) {
        this.protectFlg = protectFlg;
    }

    @JsonProperty("judgmentScore")
    public Integer getJudgmentScore() {
        return judgmentScore;
    }

    @JsonProperty("judgmentScore")
    public void setJudgmentScore(Integer judgmentScore) {
        this.judgmentScore = judgmentScore;
    }

    @JsonProperty("faceID")
    public String getFaceID() {
        return faceID;
    }

    @JsonProperty("faceID")
    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }
}
