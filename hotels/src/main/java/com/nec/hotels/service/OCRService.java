package com.nec.hotels.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nec.exception.HotelCheckinException;
import com.nec.facerecognition.gateway.FaceGateway;
import com.nec.facerecognition.model.request.FaceRecogniseRequest;
import com.nec.facerecognition.model.request.RecogniseReqData;
import com.nec.facerecognition.model.request.RecogniseReqHeader;
import com.nec.facerecognition.model.response.RecogniseRes;
import com.nec.hotels.entity.Address;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.model.WebhookResponse;
import com.nec.hotels.utils.CustomMultipartFile;
import com.nec.hotels.utils.DateUtils;
import com.nec.hotels.utils.FileUtils;
import com.nec.ocr.enums.DocumentPage;
import com.nec.ocr.enums.DocumentType;
import com.nec.ocr.gateway.OCRGateway;
import com.nec.ocr.model.OCRResponse;
import com.nec.pms.model.guest.Name;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Service
public class OCRService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OCRService.class);
	
	private final OCRGateway ocrGateway;
	private final String webhookUrl;
	private FaceGateway faceGateway;
	
	
	@Autowired
	public OCRService(final OCRGateway ocrGateway, @Value("${webhook.url}") String webhookUrl,
			final FaceGateway faceGateway) {
		this.ocrGateway = ocrGateway;
		this.webhookUrl = webhookUrl;
		this.faceGateway = faceGateway;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public OCRResponse ocrRequest(String bookingId, Guest guest, String fileUrl, DocumentType docType, String docIssuePlace,
			DocumentPage docPage, boolean ocrActive) {
		if(!ocrActive) {
			return new OCRResponse();
		}
		RequestBody part = null;
			
		try {
			MultipartFile customMultipartFile = new CustomMultipartFile(FileUtils.getFileUrlToByteArray(fileUrl), bookingId +getFilename(fileUrl));
			customMultipartFile.transferTo(((CustomMultipartFile)customMultipartFile).getFile());
			if (fileUrl.substring(fileUrl.lastIndexOf(".")).equalsIgnoreCase("pdf")) {
				part = RequestBody.create(MediaType.parse("application/pdf"), FileUtils.getFileUrlToByteArray(fileUrl));
			} else {
				part = RequestBody.create(MediaType.parse("application/octet-stream"), ((CustomMultipartFile)customMultipartFile).getFile());
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new HotelCheckinException("Document Upload Failed ", HttpStatus.BAD_REQUEST);
		}
		MultipartBody req = new MultipartBody.Builder().addFormDataPart("doc_type", docType.getType())
				.addFormDataPart("doc_img", getFilename(fileUrl), part)
				.addFormDataPart("doc_page", docPage.getType())
				.addFormDataPart("customer_id", guest.getId().toString()).build();
		
		return ocrGateway.ocrRequest(req);
		
	}
	
	
	private String getFilename(String url) {
		return url.substring( url.lastIndexOf('/')+1, url.length() );
	}
	
	
	
	/**
	 * 
	 * @param <T>
	 * @param bookingId
	 * @param guest
	 * @param response
	 * @return
	 */
	public <T extends Guest> boolean validateDocumentFront(Booking booking, T guest, OCRResponse response, DocumentType documentType, int groupId) {
		Integer docNamePercentage = this.calculateNameMatchPercentage(guest.getName(), response.getName());
		if(docNamePercentage < 0 ) {
			throw new HotelCheckinException("Name does not match with uploaded documents", HttpStatus.BAD_REQUEST);
		}
		Integer docImagePercentage = this.calculateFaceMatchPercentage(booking.getId(), response.getFaceData(), guest.getId().toString(), groupId);
		String birthDate = response.getDateOfBirth();
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
		guest.setNationality(response.getNationality());
		guest.setDateOfBirth(DateUtils.getMillisFromTimestamp(DateUtils.convertDate(response.getDateOfBirth()), "MMMM dd, yyyy"));
		if (StringUtils.isNotEmpty(response.getDateOfExpiry()) && !Objects.isNull(booking.getCheckInTime())){
			if(Boolean.TRUE.equals(checkForExpied(booking.getCheckInTime(),booking.getDepartureTime(), response.getDateOfExpiry()))){
				throw new HotelCheckinException(documentType.getType() + " expired, please upload a valid document”", HttpStatus.BAD_REQUEST);	
			}
			if(Boolean.TRUE.equals(checkForExpiry(booking.getCheckInTime(),booking.getDepartureTime(), response.getDateOfExpiry()))){
				throw new HotelCheckinException(documentType.getType() + " expiring before checkout, please upload a valid document", HttpStatus.BAD_REQUEST);	
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param faceData
	 * @return
	 */
	private int calculateFaceMatchPercentage(String bookingId, String faceData, String guestId, int groupId) {
		RecogniseReqHeader reqHeader = new RecogniseReqHeader();
		reqHeader.setReqDatetime(java.time.LocalDateTime.now().toString());
		RecogniseReqData reqData = new RecogniseReqData();
		reqData.setFaceImage(faceData);
		reqData.setThreshold(65);
		reqData.setItemFlag(1);
		reqData.setFaceFlag(1);
		int[] groupIds = {groupId};
;		reqData.setGroupIDs(groupIds);
		ArrayList<String> targetedUniqueIds = new ArrayList<>();
		targetedUniqueIds.add(bookingId);
		reqData.setTargetUniqueIDs(targetedUniqueIds);
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
	
	/**
	 * Validate document back address
	 * @param address
	 * @param ocrResponse
	 * @return
	 */
	public boolean validateDocumentBack(Booking booking, final Address address, final OCRResponse ocrResponse) {
		if(Objects.isNull(address)) {
			return true;
		}
		
		if(!StringUtils.isEmpty(ocrResponse.getAddress())) {
			//todo Address match
			
			return true;
		}
		return false;
	}
	
	
	/**
	 * checked for document expire before checkout
	 * @param checkoutTime
	 * @param ocrResponse
	 * @return
	 */
	public boolean checkForExpiry(final long checkinTime , final long checkoutTime, final String dateOfExpiry) {
		if(Objects.nonNull(dateOfExpiry)) {
			long ocrDOE = DateUtils.getMillisFromTimestamp(DateUtils.convertDate(dateOfExpiry), "MMMM dd, yyyy");
			int checkinDate[] = DateUtils.getDayDateYearFromTimeStamp(checkinTime);
			int checkoutDate[] = DateUtils.getDayDateYearFromTimeStamp(checkoutTime);
			int ocrDate[] = DateUtils.getDayDateYearFromTimeStamp(ocrDOE);
			checkinDate[2] = convertYear(checkinDate[2]);
			checkoutDate[2] = convertYear(checkoutDate[2]);
			ocrDate[2] = convertYear(ocrDate[2]);
			
			if(ocrDate[2] > checkinDate[2] && ocrDate[2] < checkoutDate[2]) {
				return true;
			}
			
			if(ocrDate[2] == checkinDate[2] && ocrDate[2] == checkoutDate[2]) {
				if(ocrDate[1] > checkinDate[1] && ocrDate[1] < checkinDate[1])
				return true;	
				
				if(ocrDate[1] == checkinDate[1] && ocrDate[1] == checkinDate[1]) {
					if(ocrDate[0] > checkinDate[0] && ocrDate[0] < checkinDate[0])
						return true;
				}
				
			}	
		}
		return false;
	}
	
    /**
     *  check for document expired
     * @param checkinTime
     * @param ocrResponse
     * @return
     */
	private boolean checkForExpied(final long checkinTime, final long checkoutTime, final String dateOfExpiry) {
		if(Objects.nonNull(dateOfExpiry)) {
			long ocrDOE = DateUtils.getMillisFromTimestamp(DateUtils.convertDate(dateOfExpiry), "MMMM dd, yyyy");
		int checkinDate[] = DateUtils.getDayDateYearFromTimeStamp(checkinTime);
		int checkoutDate[] = DateUtils.getDayDateYearFromTimeStamp(checkoutTime);
		int ocrDate[] = DateUtils.getDayDateYearFromTimeStamp(ocrDOE);
		checkinDate[2] = convertYear(checkinDate[2]);
		checkoutDate[2] = convertYear(checkoutDate[2]);
		ocrDate[2] = convertYear(ocrDate[2]);
		
		if(ocrDate[2] < checkinDate[2] && ocrDate[2] < checkoutDate[2]) {
			return true;
		}
		
		if(ocrDate[2] == checkinDate[2] && ocrDate[2] == checkoutDate[2]) {
			if(ocrDate[1] < checkinDate[1] && ocrDate[1] < checkinDate[1])
			return true;	
			
			if(ocrDate[1] == checkinDate[1] && ocrDate[1] == checkinDate[1]) {
				if(ocrDate[0] < checkinDate[0] && ocrDate[0] < checkinDate[0])
					return true;
			}
			
		}	
		
		}
		return false;
	}
	
	private static int convertYear(int year) {
		String subYear = Integer.toString(year);
		subYear = subYear.substring(2, subYear.length());
		return Integer.parseInt(subYear);
	}

	
	

}
