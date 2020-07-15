
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmountData {

    @JsonProperty("currencyCode")
    private String currencyCode;
    @JsonProperty("decimals")
    private String decimals;

    @JsonProperty("currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("currencyCode")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("decimals")
    public String getDecimals() {
        return decimals;
    }

    @JsonProperty("decimals")
    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

}
