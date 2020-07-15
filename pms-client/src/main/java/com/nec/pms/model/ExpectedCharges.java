package com.nec.pms.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpectedCharges {

	private UUID id;

	@JsonProperty("grossTotal")
	private Integer grossTotal;

	@JsonProperty("totalTax")
	private Integer totalTax;

	@JsonProperty("packageTotal")
	private Integer packageTotal;

	@JsonProperty("addOnPackageTotal")
	private Integer addOnPackageTotal;

	@JsonProperty("dailyBreakdown")
	private List<DailyBreakdown> dailyBreakdown = null;

	@JsonProperty("grossTotal")
	public Integer getGrossTotal() {
		return grossTotal;
	}

	@JsonProperty("grossTotal")
	public void setGrossTotal(Integer grossTotal) {
		this.grossTotal = grossTotal;
	}

	@JsonProperty("totalTax")
	public Integer getTotalTax() {
		return totalTax;
	}

	@JsonProperty("totalTax")
	public void setTotalTax(Integer totalTax) {
		this.totalTax = totalTax;
	}

	@JsonProperty("packageTotal")
	public Integer getPackageTotal() {
		return packageTotal;
	}

	@JsonProperty("packageTotal")
	public void setPackageTotal(Integer packageTotal) {
		this.packageTotal = packageTotal;
	}

	@JsonProperty("addOnPackageTotal")
	public Integer getAddOnPackageTotal() {
		return addOnPackageTotal;
	}

	@JsonProperty("addOnPackageTotal")
	public void setAddOnPackageTotal(Integer addOnPackageTotal) {
		this.addOnPackageTotal = addOnPackageTotal;
	}

	@JsonProperty("dailyBreakdown")
	public List<DailyBreakdown> getDailyBreakdown() {
		return dailyBreakdown;
	}

	@JsonProperty("dailyBreakdown")
	public void setDailyBreakdown(List<DailyBreakdown> dailyBreakdown) {
		this.dailyBreakdown = dailyBreakdown;
	}

}
