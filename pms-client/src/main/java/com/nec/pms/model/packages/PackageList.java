
package com.nec.pms.model.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageList {

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private PackageData packageData;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("action")
    private Object action;
    @JsonProperty("signature")
    private Object signature;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Object id;

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("data")
    public PackageData getData() {
        return packageData;
    }

    @JsonProperty("data")
    public void setData(PackageData packageData) {
        this.packageData = packageData;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("action")
    public Object getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(Object action) {
        this.action = action;
    }

    @JsonProperty("signature")
    public Object getSignature() {
        return signature;
    }

    @JsonProperty("signature")
    public void setSignature(Object signature) {
        this.signature = signature;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public Object getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Object id) {
        this.id = id;
    }

}
