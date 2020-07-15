
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {

    @JsonProperty("attributes")
    private AmountData attributes;
    @JsonProperty("value")
    private String value;

    @JsonProperty("attributes")
    public AmountData getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(AmountData attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

}
