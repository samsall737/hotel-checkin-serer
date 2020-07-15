package com.nec.hotels.model.checkin;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.CardDetails;

public class PreCheckinDTO {

	@JsonProperty("card_detail")
	private CardDetails creditCard;

	@NotNull
	@JsonProperty("pay_at_desk")
	private boolean payAtDesk;

	public CardDetails getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CardDetails creditCard) {
		this.creditCard = creditCard;
	}

	public boolean isPayAtDesk() {
		return payAtDesk;
	}

	public void setPayAtDesk(boolean payAtDesk) {
		this.payAtDesk = payAtDesk;
	}
}
