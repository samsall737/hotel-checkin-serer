
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package {

    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("shortDescription")
    private ShortDescription shortDescription;
    @JsonProperty("packageDetails")
    private PackageDetails packageDetails;

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    @JsonProperty("shortDescription")
    public ShortDescription getShortDescription() {
        return shortDescription;
    }

    @JsonProperty("shortDescription")
    public void setShortDescription(ShortDescription shortDescription) {
        this.shortDescription = shortDescription;
    }

    @JsonProperty("packageDetails")
    public PackageDetails getPackageDetails() {
        return packageDetails;
    }

    @JsonProperty("packageDetails")
    public void setPackageDetails(PackageDetails packageDetails) {
        this.packageDetails = packageDetails;
    }
    
    @Override
    public boolean equals(Object obj) {
    	return obj.equals(attributes.getPackageCode()) ;
    }

}
