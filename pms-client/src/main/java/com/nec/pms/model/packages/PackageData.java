
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageData {

    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("packages")
    private Packages packages;
/*
    @JsonProperty("items")
    private Items items;
*/

    public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	@JsonProperty("packages")
    public Packages getPackages() {
        return packages;
    }

    @JsonProperty("packages")
    public void setPackages(Packages packages) {
        this.packages = packages;
    }

  /*  @JsonProperty("items")
    public Items getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items items) {
        this.items = items;
    }*/

}
