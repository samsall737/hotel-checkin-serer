package com.nec.hotels.model.guest;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nec.hotels.entity.GuestDocuments;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.Title;

public abstract class GuestDTO {

	@NotBlank
    protected UUID id;

    @JsonProperty("first_name")
    protected String firstName;

    @JsonProperty("last_name")
    protected String lastName;

    protected Title title;

    protected String nationality;

    @JsonProperty("is_vip")
    protected boolean isVIP;

    @JsonProperty("guest_uploads")
    protected GuestDocuments guestDocuments;

    @JsonProperty("date_of_birth")
    protected long dateOfBirth;

    @JsonProperty("document_name_validation")
    protected Integer documentNameValidation;

    @JsonProperty("document_image_validation")
    protected Integer documentImageValidation;

    @JsonIgnore
    protected String pmsGuestId;

    @JsonProperty("is_frro")
    protected boolean isFrro = false;

    @JsonIgnore
    protected GuestType type;

    public GuestDTO() {
    }

    public GuestDTO(UUID id, String firstName, String lastName, Title title, String nationality, boolean isVIP, GuestDocuments guestDocuments, long dateOfBirth, Integer documentNameValidation, Integer documentImageValidation, String pmsGuestId, boolean isFrro, GuestType type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.nationality = nationality;
        this.isVIP = isVIP;
        this.guestDocuments = guestDocuments;
        this.dateOfBirth = dateOfBirth;
        this.documentNameValidation = documentNameValidation;
        this.documentImageValidation = documentImageValidation;
        this.pmsGuestId = pmsGuestId;
        this.isFrro = isFrro;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public GuestDocuments getGuestDocuments() {
        return guestDocuments;
    }

    public void setGuestDocuments(GuestDocuments guestDocuments) {
        this.guestDocuments = guestDocuments;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getDocumentNameValidation() {
        return documentNameValidation;
    }

    public void setDocumentNameValidation(Integer documentNameValidation) {
        this.documentNameValidation = documentNameValidation;
    }

    public Integer getDocumentImageValidation() {
        return documentImageValidation;
    }

    public void setDocumentImageValidation(Integer documentImageValidation) {
        this.documentImageValidation = documentImageValidation;
    }

    public String getPmsGuestId() {
        return pmsGuestId;
    }

    public void setPmsGuestId(String pmsGuestId) {
        this.pmsGuestId = pmsGuestId;
    }

    @JsonIgnore
    public boolean isFrro() {
        return isFrro;
    }

    public void setFrro(boolean frro) {
        isFrro = frro;
    }

    public GuestType getType() {
        return type;
    }

    public void setType(GuestType type) {
        this.type = type;
    }

    @JsonIgnore
    public abstract boolean isFaceRequired();
    
    @JsonIgnore
    public abstract boolean isDocumentRequired();
}
