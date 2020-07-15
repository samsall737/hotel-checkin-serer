package com.nec.hotels.service;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.facerecognition.gateway.FaceGateway;
import com.nec.facerecognition.model.request.FaceRecogniseRequest;
import com.nec.facerecognition.model.request.RecogniseReqData;
import com.nec.facerecognition.model.request.RecogniseReqHeader;
import com.nec.facerecognition.model.response.RecogniseRes;
import com.nec.hotels.dao.GuestRepository;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.model.WebhookResponse;

@Service
public class FaceRecognitionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionService.class);
	
	private final FaceGateway faceGateway;
	private final GuestRepository guestRepository;
	
	@Autowired
	public FaceRecognitionService(final FaceGateway faceGateway, final GuestRepository guestRepository) {
		this.faceGateway = faceGateway;
		this.guestRepository = guestRepository;
	}
	
	
	/*
	 * This function is to check if document is valid from OCR response
	 */
	public boolean documentValidation(WebhookResponse response, int groupId) {
		LOGGER.info("==================documentValidation============== {}", response);
		if (Objects.isNull(response.getCustomerId())) {
			return false;
		}
		Optional<Guest> primaryGuest = guestRepository.findById(response.getCustomerId());
		if (primaryGuest.isPresent()) {
			return this.validate(primaryGuest.get(), response,groupId);
		}
		Optional<Guest> secondaryGuest = guestRepository.findById(response.getCustomerId());
		if (secondaryGuest.isPresent()) {
			return this.validate(secondaryGuest.get(), response, groupId);
		}
		return false;

	}
	
	
	private <T extends Guest> boolean validate(T guest, WebhookResponse response, int groupId) {
		Integer docNamePercentage = this.calculateNameMatchPercentage(guest.getFirstName() + " " + guest.getLastName(),
				response.getName());
		Integer docImagePercentage = this.calculateFaceMatchPercentage(response.getFaceData(), groupId);
		if (Objects.isNull(guest.getDocumentNameValidation())) {
			guest.setDocumentNameValidation(docNamePercentage);
		} else {
			guest.setDocumentNameValidation((guest.getDocumentNameValidation() + docNamePercentage) / 2);
		}
		if (Objects.isNull(guest.getDocumentImageValidation())) {
			guest.setDocumentImageValidation(docImagePercentage);
		} else {
			guest.setDocumentImageValidation((guest.getDocumentImageValidation() + docImagePercentage) / 2);
		}
		return true;
	}
	
	/**
	 * calculate face match percentage
	 * @param faceData
	 * @return
	 */
	private int calculateFaceMatchPercentage(String faceData, int groupId) {
		RecogniseReqHeader reqHeader = new RecogniseReqHeader();
		reqHeader.setReqDatetime(java.time.LocalDateTime.now().toString());
		RecogniseReqData reqData = new RecogniseReqData();
		reqData.setFaceImage(faceData);
		reqData.setThreshold(65);
		reqData.setItemFlag(1);
		reqData.setFaceFlag(1);
		int[] groupIds = {groupId};
		reqData.setGroupIDs(groupIds);
		FaceRecogniseRequest request = new FaceRecogniseRequest();
		request.setReqHeader(reqHeader);
		request.setReqData(reqData);
		LOGGER.info("Recognise Face request {} :", request);
		RecogniseRes response = faceGateway.recogniseFace(request);
		if(Objects.isNull(response)) {
			return 0;
		}
		return response.getResData().getAuthenticateList()
				.get(response.getResData().getAuthenticateList().size() - 1).getVerifyScore();
	}

	/*
	 * This function is to calculate the percentage of name match
	 */
	private int calculateNameMatchPercentage(String bookingName, String documentName) {
		LOGGER.info("==================calculateNameMatchPercentage============== {} | {}", bookingName, documentName);
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
		int distance = levenshteinDistance.apply(bookingName, documentName);
		int nameLength = (bookingName.length() > documentName.length()) ? bookingName.length() : documentName.length();
		return (nameLength - distance) / nameLength * 100;
	}
	
	
	

}
