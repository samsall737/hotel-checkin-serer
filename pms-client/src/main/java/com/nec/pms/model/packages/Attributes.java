
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

    @JsonProperty("packageCode")
    private String packageCode;
    @JsonProperty("calculationRule")
    private String calculationRule;
    @JsonProperty("postingRhythm")
    private String postingRhythm;
    @JsonProperty("includedInRate")
    private String includedInRate;
    @JsonProperty("addRateSeprateLine")
    private String addRateSeprateLine;
    @JsonProperty("addRateCombinedLine")
    private String addRateCombinedLine;
    @JsonProperty("sellSeparate")
    private String sellSeparate;

    @JsonProperty("packageCode")
    public String getPackageCode() {
        return packageCode;
    }

    @JsonProperty("packageCode")
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    @JsonProperty("calculationRule")
    public String getCalculationRule() {
        return calculationRule;
    }

    @JsonProperty("calculationRule")
    public void setCalculationRule(String calculationRule) {
        this.calculationRule = calculationRule;
    }

    @JsonProperty("postingRhythm")
    public String getPostingRhythm() {
        return postingRhythm;
    }

    @JsonProperty("postingRhythm")
    public void setPostingRhythm(String postingRhythm) {
        this.postingRhythm = postingRhythm;
    }

    @JsonProperty("includedInRate")
    public String getIncludedInRate() {
        return includedInRate;
    }

    @JsonProperty("includedInRate")
    public void setIncludedInRate(String includedInRate) {
        this.includedInRate = includedInRate;
    }

    @JsonProperty("addRateSeprateLine")
    public String getAddRateSeprateLine() {
        return addRateSeprateLine;
    }

    @JsonProperty("addRateSeprateLine")
    public void setAddRateSeprateLine(String addRateSeprateLine) {
        this.addRateSeprateLine = addRateSeprateLine;
    }

    @JsonProperty("addRateCombinedLine")
    public String getAddRateCombinedLine() {
        return addRateCombinedLine;
    }

    @JsonProperty("addRateCombinedLine")
    public void setAddRateCombinedLine(String addRateCombinedLine) {
        this.addRateCombinedLine = addRateCombinedLine;
    }

    @JsonProperty("sellSeparate")
    public String getSellSeparate() {
        return sellSeparate;
    }

    @JsonProperty("sellSeparate")
    public void setSellSeparate(String sellSeparate) {
        this.sellSeparate = sellSeparate;
    }

}
