package com.nec.pms.model.checkin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.pms.model.FlightDetails;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrecheckinDetailsDTO {

	@JsonProperty("guests")
	private List<PmsGuestDTO> pmsGuestDTOS;

	private List<Package> packages;

	private FlightDetails flightDetails;
	
	private ETA eta;

	
	private String comment;

	public List<PmsGuestDTO> getPmsGuestDTOS() {
		return pmsGuestDTOS;
	}

	public void setPmsGuestDTOS(List<PmsGuestDTO> pmsGuestDTOS) {
		this.pmsGuestDTOS = pmsGuestDTOS;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List< Package> packages) {
		this.packages = packages;
	}

	public FlightDetails getFlightDetails() {
		return flightDetails;
	}

	public void setFlightDetails(FlightDetails flightDetails) {
		this.flightDetails = flightDetails;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ETA getEta() {
		return eta;
	}

	public void setEta(ETA eta) {
		this.eta = eta;
	}
	
}
