package com.nec.pms.model;

import java.util.List;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyBreakdown {

	private UUID id;

	@JsonProperty("grossDailyTotal")
	private Integer grossDailyTotal;

	@JsonProperty("grossDailyTax")
	private Integer grossDailyTax;

	@JsonProperty("grossDailyAddOnPackage")
	private Integer grossDailyAddOnPackage;

	@JsonProperty("grossDailyPackage")
	private Integer grossDailyPackage;

	@JsonProperty("roomRatesAndPackage")
	private List<RoomRatesAndPackage> roomRatesAndPackage = null;

	@JsonProperty("taxesAndFees")
	private List<TaxesAndFee> taxesAndFees = null;

	private ExpectedCharges expectedCharges;

	@JsonProperty("grossDailyTotal")
	public Integer getGrossDailyTotal() {
		return grossDailyTotal;
	}

	@JsonProperty("grossDailyTotal")
	public void setGrossDailyTotal(Integer grossDailyTotal) {
		this.grossDailyTotal = grossDailyTotal;
	}

	@JsonProperty("grossDailyTax")
	public Integer getGrossDailyTax() {
		return grossDailyTax;
	}

	@JsonProperty("grossDailyTax")
	public void setGrossDailyTax(Integer grossDailyTax) {
		this.grossDailyTax = grossDailyTax;
	}

	@JsonProperty("grossDailyAddOnPackage")
	public Integer getGrossDailyAddOnPackage() {
		return grossDailyAddOnPackage;
	}

	@JsonProperty("grossDailyAddOnPackage")
	public void setGrossDailyAddOnPackage(Integer grossDailyAddOnPackage) {
		this.grossDailyAddOnPackage = grossDailyAddOnPackage;
	}

	@JsonProperty("grossDailyPackage")
	public Integer getGrossDailyPackage() {
		return grossDailyPackage;
	}

	@JsonProperty("grossDailyPackage")
	public void setGrossDailyPackage(Integer grossDailyPackage) {
		this.grossDailyPackage = grossDailyPackage;
	}

	@JsonProperty("roomRatesAndPackage")
	public List<RoomRatesAndPackage> getRoomRatesAndPackage() {
		return roomRatesAndPackage;
	}

	@JsonProperty("roomRatesAndPackage")
	public void setRoomRatesAndPackage(List<RoomRatesAndPackage> roomRatesAndPackage) {
		this.roomRatesAndPackage = roomRatesAndPackage;
	}

	@JsonProperty("taxesAndFees")
	public List<TaxesAndFee> getTaxesAndFees() {
		return taxesAndFees;
	}

	@JsonProperty("taxesAndFees")
	public void setTaxesAndFees(List<TaxesAndFee> taxesAndFees) {
		this.taxesAndFees = taxesAndFees;
	}

}
