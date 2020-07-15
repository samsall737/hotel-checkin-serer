package com.nec.hotels.model.guest;

import com.nec.hotels.entity.Guest;
import com.nec.hotels.enums.GuestType;

public class GuestFactory {
    public GuestDTO getGuest(Guest guest, GuestType guestType){
        switch (guestType){
            case PRIMARY:
                return new PrimaryGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFRRO(),guest.getType(),guest.getIsdCode(),guest.getContactNumber(),guest.getEmail(),guest.getAddress(),guest.getSignatureUrl(),guest.getRegcardUrl(),guest.getImageUrl());
            case KIDS:
                return new KidsGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFRRO(),guest.getType());
            case SHARED_GUEST:
                return new SharedGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFRRO(),guest.getType());
            case ACCOMPANY_GUEST:
                return new AccompanyGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFRRO(),guest.getType());
            default:
                break;
        }
        return null;
    }

	public GuestDTO getGuest(SecondaryGuest guest, GuestType guestType) {
	       switch (guestType){
            case KIDS:
               return new KidsGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFrro(),guest.getType());
           case SHARED_GUEST:
               return new SharedGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFrro(),guest.getType());
           case ACCOMPANY_GUEST:
               return new AccompanyGuest(guest.getId(),guest.getFirstName(),guest.getLastName(),guest.getTitle(),guest.getNationality(),guest.isVIP(),guest.getGuestDocuments(), guest.getDateOfBirth(),guest.getDocumentNameValidation(),guest.getDocumentImageValidation(),guest.getPmsGuestId(),guest.isFrro(),guest.getType());
           default:
               break;
       }
       return null;
   }
	}

