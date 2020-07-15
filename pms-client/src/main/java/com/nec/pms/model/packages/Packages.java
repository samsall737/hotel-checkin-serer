
package com.nec.pms.model.packages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Packages {

    @JsonProperty("package")
    private List<Package> _package = null;

    @JsonProperty("package")
    public List<Package> getPackage() {
        return _package;
    }

    @JsonProperty("package")
    public void setPackage(List<Package> _package) {
        this._package = _package;
    }

}
