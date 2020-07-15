package com.nec.pms.model.checkin;

public class Package {

	private String packageCode;
	private String value;
	private Long amount;
	private String startDate;
	private String endDate;
	private int quantity;
	public Package() {
	}

	public Package(String value, Long admount, int quantity, String packageCode, String startDate, String endDate) {
		super();
		this.value = value;
		this.amount = admount;
		this.packageCode = packageCode;
		this.endDate = endDate;
		this.startDate = startDate;
		this.quantity = quantity;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
