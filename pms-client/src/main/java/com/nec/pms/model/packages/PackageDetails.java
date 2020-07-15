
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageDetails {

    @JsonProperty("attributes")
    private Attributes attributes;
    
    @JsonProperty("startDate")
    private Values startDate;
    @JsonProperty("endDate")
    private Values values;
    @JsonProperty("amount")
    private Amount amount;

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("startDate")
    public Values getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(Values startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public Values getEndDate() {
        return values;
    }

    @JsonProperty("endDate")
    public void setEndDate(Values values) {
        this.values = values;
    }

    @JsonProperty("amount")
    public Amount getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

}
