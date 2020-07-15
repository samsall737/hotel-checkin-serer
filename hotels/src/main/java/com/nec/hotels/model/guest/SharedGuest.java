package com.nec.hotels.model.guest;


import java.util.UUID;

import com.nec.hotels.entity.GuestDocuments;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.Title;

public class SharedGuest extends SecondaryGuest {
	public SharedGuest() {
		setType(GuestType.SHARED_GUEST);
	}

	public SharedGuest(UUID id, String firstName, String lastName, Title title, String nationality, boolean isVIP, GuestDocuments guestDocuments, long dateOfBirth, Integer documentNameValidation, Integer documentImageValidation, String pmsGuestId, boolean isFrro, GuestType type) {
		super(id, firstName, lastName, title, nationality, isVIP, guestDocuments, dateOfBirth, documentNameValidation, documentImageValidation, pmsGuestId, isFrro, type);
	}

	@Override
	public boolean isFaceRequired() {
		return false;
	}

	@Override
	public boolean isDocumentRequired() {
		return false;
	}
}
