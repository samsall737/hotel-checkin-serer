
package com.nec.facerecognition.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "frontalScore",
    "eyeDistance",
    "reliability",
    "lEyeX",
    "lEyeY",
    "rEyeX",
    "rEyeY",
    "faceLeft",
    "faceTop",
    "faceRight",
    "faceBottom",
    "roll"
})
public class DetectFaceData {

    @JsonProperty("frontalScore")
    private Double frontalScore;
    @JsonProperty("eyeDistance")
    private Integer eyeDistance;
    @JsonProperty("reliability")
    private Double reliability;
    @JsonProperty("lEyeX")
    private Integer lEyeX;
    @JsonProperty("lEyeY")
    private Integer lEyeY;
    @JsonProperty("rEyeX")
    private Integer rEyeX;
    @JsonProperty("rEyeY")
    private Integer rEyeY;
    @JsonProperty("faceLeft")
    private Integer faceLeft;
    @JsonProperty("faceTop")
    private Integer faceTop;
    @JsonProperty("faceRight")
    private Integer faceRight;
    @JsonProperty("faceBottom")
    private Integer faceBottom;
    @JsonProperty("roll")
    private Double roll;

    @JsonProperty("frontalScore")
    public Double getFrontalScore() {
        return frontalScore;
    }

    @JsonProperty("frontalScore")
    public void setFrontalScore(Double frontalScore) {
        this.frontalScore = frontalScore;
    }

    @JsonProperty("eyeDistance")
    public Integer getEyeDistance() {
        return eyeDistance;
    }

    @JsonProperty("eyeDistance")
    public void setEyeDistance(Integer eyeDistance) {
        this.eyeDistance = eyeDistance;
    }

    @JsonProperty("reliability")
    public Double getReliability() {
        return reliability;
    }

    @JsonProperty("reliability")
    public void setReliability(Double reliability) {
        this.reliability = reliability;
    }

    @JsonProperty("lEyeX")
    public Integer getLEyeX() {
        return lEyeX;
    }

    @JsonProperty("lEyeX")
    public void setLEyeX(Integer lEyeX) {
        this.lEyeX = lEyeX;
    }

    @JsonProperty("lEyeY")
    public Integer getLEyeY() {
        return lEyeY;
    }

    @JsonProperty("lEyeY")
    public void setLEyeY(Integer lEyeY) {
        this.lEyeY = lEyeY;
    }

    @JsonProperty("rEyeX")
    public Integer getREyeX() {
        return rEyeX;
    }

    @JsonProperty("rEyeX")
    public void setREyeX(Integer rEyeX) {
        this.rEyeX = rEyeX;
    }

    @JsonProperty("rEyeY")
    public Integer getREyeY() {
        return rEyeY;
    }

    @JsonProperty("rEyeY")
    public void setREyeY(Integer rEyeY) {
        this.rEyeY = rEyeY;
    }

    @JsonProperty("faceLeft")
    public Integer getFaceLeft() {
        return faceLeft;
    }

    @JsonProperty("faceLeft")
    public void setFaceLeft(Integer faceLeft) {
        this.faceLeft = faceLeft;
    }

    @JsonProperty("faceTop")
    public Integer getFaceTop() {
        return faceTop;
    }

    @JsonProperty("faceTop")
    public void setFaceTop(Integer faceTop) {
        this.faceTop = faceTop;
    }

    @JsonProperty("faceRight")
    public Integer getFaceRight() {
        return faceRight;
    }

    @JsonProperty("faceRight")
    public void setFaceRight(Integer faceRight) {
        this.faceRight = faceRight;
    }

    @JsonProperty("faceBottom")
    public Integer getFaceBottom() {
        return faceBottom;
    }

    @JsonProperty("faceBottom")
    public void setFaceBottom(Integer faceBottom) {
        this.faceBottom = faceBottom;
    }

    @JsonProperty("roll")
    public Double getRoll() {
        return roll;
    }

    @JsonProperty("roll")
    public void setRoll(Double roll) {
        this.roll = roll;
    }


}
