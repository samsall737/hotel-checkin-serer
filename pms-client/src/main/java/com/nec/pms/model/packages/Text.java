
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Text {

    @JsonProperty("attributes")
    private Object attributes;
    @JsonProperty("textElement")
    private String textElement;

    @JsonProperty("attributes")
    public Object getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("textElement")
    public String getTextElement() {
        return textElement;
    }

    @JsonProperty("textElement")
    public void setTextElement(String textElement) {
        this.textElement = textElement;
    }

}
