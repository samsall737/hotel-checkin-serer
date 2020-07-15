package com.nec.hotels.model.guest;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.hotels.entity.Address;
import com.nec.hotels.entity.GuestDocuments;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.Title;

public class PrimaryGuest extends GuestDTO {

    @JsonProperty("isd_code")
    private int isdCode;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("email_id")
    private String email;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("signature_url")
    private String signatureUrl;

    @JsonProperty("regcard_url")
    private String regcardUrl;
    
    @JsonProperty("image_url")
    private String imageUrl;

    public PrimaryGuest() {
        setType(GuestType.PRIMARY);
    }

    @Override
    public boolean isFaceRequired() {
        return true;
    }

    @Override
    public boolean isDocumentRequired() {
        return true;
    }

    public PrimaryGuest(UUID id, String firstName, String lastName, Title title, String nationality, boolean isVIP, GuestDocuments guestDocuments, long dateOfBirth, Integer documentNameValidation, Integer documentImageValidation, String pmsGuestId, boolean isFrro, GuestType type, int isdCode, String contactNumber, String email, Address address, String signatureUrl, String regcardUrl, String imageUrl) {
        super(id, firstName, lastName, title, nationality, isVIP, guestDocuments, dateOfBirth, documentNameValidation, documentImageValidation, pmsGuestId, isFrro, type);
        this.isdCode = isdCode;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.signatureUrl = signatureUrl;
        this.regcardUrl = regcardUrl;
        this.imageUrl = imageUrl;
    }

    public int getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(int isdCode) {
        this.isdCode = isdCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public String getRegcardUrl() {
        return regcardUrl;
    }

    public void setRegcardUrl(String regcardUrl) {
        this.regcardUrl = regcardUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
