package com.nec.hotels.service;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nec.document.gateway.DocumentGateway;
import com.nec.document.model.UploadResponse;
import com.nec.exception.FileStorageException;
import com.nec.exception.HotelCheckinException;
import com.nec.facerecognition.gateway.FaceGateway;
import com.nec.facerecognition.model.request.FaceRecogniseRequest;
import com.nec.facerecognition.model.request.RecogniseReqData;
import com.nec.facerecognition.model.request.RecogniseReqHeader;
import com.nec.facerecognition.model.request.RegisterFaceDTO;
import com.nec.facerecognition.model.response.RecogniseRes;
import com.nec.hotels.dao.BookingRepository;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.enums.ValidExtensions;
import com.nec.hotels.utils.Constants;
import com.nec.hotels.utils.DateUtils;
import com.nec.hotels.utils.FileUtils;
import com.nec.ocr.gateway.OCRGateway;

@Service
public class FileService { 
	
	Logger logger = LoggerFactory.getLogger(FileService.class);


	private final BookingRepository bookingRepo;
	private final FaceGateway faceGateway;
	private final DocumentGateway documentGateway;

	@Autowired
	public FileService (final FaceGateway faceGateway,
			BookingRepository bookingRepo, @Value("${webhook.url}") String webhookUrl, 
			OCRGateway ocrGateway, final DocumentGateway documentGateway) {
		this.faceGateway = faceGateway;
		this.bookingRepo = bookingRepo;
		this.documentGateway = documentGateway;
	}

	/**
	 * This function is to store a file
	 */
	public UploadResponse storeFile(String bookingId, UUID guestId, MultipartFile file, String type) {
		logger.info("==================storeFile============== {} | {} | {} | {}", bookingId, guestId, file, type);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = fileName.substring(fileName.lastIndexOf('.'));
		if (!ValidExtensions.contains(extension.toLowerCase())) {
			throw new HotelCheckinException("File Extension not valid", HttpStatus.BAD_REQUEST);
		}
		fileName = type + "_" + guestId.toString() + extension;
		if (fileName.contains("..")) {
			throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
		}
		return uploadFile(file, null , bookingId + '/' + type);
	}

	/**
	 * This function is to add a face in face SDK
	 */
	public boolean registerFace(String bookingId, String fileUrl, String guestName, String type, String guestId, boolean faceRecognitionActive, int groupId) {
		if(!faceRecognitionActive) {
			return true;
		}
		
		logger.info("==================registerFace============== {} | {} | {} | {}", bookingId, fileUrl, guestName,
				type);
		RegisterFaceDTO registerFace = new RegisterFaceDTO();
		String byteImage = "";
		try {
			byte[] fileContent = FileUtils.getFileUrlToByteArray(fileUrl);
			byteImage = Base64.encodeBase64String(fileContent);
		} catch (IOException ex) {
			throw new HotelCheckinException(ex, "File not found " + fileUrl, HttpStatus.NOT_FOUND);
		}
		registerFace.setReqHeader(java.time.LocalDateTime.now().toString());
		int[] groupIds = {groupId};
		registerFace.setReqData(bookingId, guestName, byteImage, guestId, 65,groupIds);
		logger.info("Register Face request {} :", registerFace);
		boolean response = faceGateway.registerFace(registerFace);
		if (!response) {
			throw new HotelCheckinException("No Face detected in the Image", HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	


	/**
	 * This function is to do a face match from face SDK
	 */
	public RecogniseRes recogniseFace(MultipartFile face) {
		logger.info("==================recogniseFace============== {}", face);
		String byteImage = "";
		try {
			byteImage = Base64.encodeBase64String(face.getBytes());
		} catch (IOException e) {
			throw new HotelCheckinException("Invalid Image", HttpStatus.BAD_REQUEST);
		}
		RecogniseReqHeader reqHeader = new RecogniseReqHeader();
		reqHeader.setReqDatetime(java.time.LocalDateTime.now().toString());
		RecogniseReqData reqData = new RecogniseReqData();
		reqData.setFaceImage(byteImage);
		reqData.setThreshold(65);
		reqData.setItemFlag(1);
		reqData.setFaceFlag(1);
		FaceRecogniseRequest request = new FaceRecogniseRequest();
		request.setReqHeader(reqHeader);
		request.setReqData(reqData);
		logger.info("Recognise Face request {} :", request);
		RecogniseRes response = faceGateway.recogniseFace(request);
		logger.info("Recognise Face {} res:", response);
		if (Objects.isNull(response)) {
			throw new HotelCheckinException("No Face in the Image" , HttpStatus.BAD_REQUEST);
		}
		if (Objects.nonNull((response.getResHeader().getErrorCode()))) {
			throw new HotelCheckinException("No Face Matched", HttpStatus.NO_CONTENT);
		}
		return response;
	}

	/*
	 * This function is to delete an uploaded file
	 */
	public void deleteFile(String bookingId, String type, String fileName) {
		logger.info("==================deleteFile============== {} | {} | {}", bookingId, type, fileName);
		if (type.equalsIgnoreCase("signature")) {
			throw new HotelCheckinException("Signature cannot be deleted", HttpStatus.BAD_REQUEST);
		}
		Optional<Booking> booking = bookingRepo.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No booking By this id", HttpStatus.BAD_REQUEST);
		}
		try {
			if (booking.get().getArrivalTime() > (DateUtils.getTodayStartDate() - Constants.MILLIS_IN_A_DAY)) {
				throw new HotelCheckinException("Cannot Remove the image one day before the arrival date",
						HttpStatus.BAD_REQUEST);
			}
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		for (Guest guest : booking.get().getGuests()) {
			if (guest.getId().equals(
					UUID.fromString(fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('.'))))) {
				this.deleteFileGeneric(guest, type);
				break;
			}
		}
		booking.get().setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
		bookingRepo.save(booking.get());
	}

	/**
	 * 
	 * @param <T>
	 * @param guest
	 * @param type
	 */
	private <T extends Guest> void deleteFileGeneric(T guest, String type) {
		if (Objects.isNull(guest.getGuestUploads())) {
			throw new HotelCheckinException("No File found", HttpStatus.NO_CONTENT);
		}		
		switch (type.toLowerCase()) {
		case "image":
			guest.setImageUrl(null);
			break;
		case "document":
			guest.setGuestDocuments(null);
			break;
		case "visa":
			guest.setGuestDocuments(null);
			break;
		default:
			throw new HotelCheckinException("Invalid Type", HttpStatus.BAD_REQUEST);
		}
	}


	
	/**
	 * Upload multipart file
	 * @param file
	 * @param bucketName
	 * @param folder
	 * @return
	 */
	public UploadResponse uploadFile(MultipartFile file, String bucketName, String folder) {
		try {
			UploadResponse response = documentGateway.upload(file, null , folder);
			return Objects.nonNull(response) ? response : null;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + file.getName() + ". Please try again!", ex);
		}
	
	}
	
	/**
	 * Upload file
	 * @param file
	 * @param bucketName
	 * @param folder
	 * @return
	 */
	public UploadResponse uploadFile(File file, String bucketName, String folder) {
		try {
			UploadResponse response = documentGateway.upload(file, null , folder);
			return Objects.nonNull(response) ? response : null;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + file.getName() + ". Please try again!", ex);
		}
	
	}
	
	public byte[] downloadFile(String regUrl, String bookingId) throws IOException {
		URL url = new URL(regUrl);
		String fileName = bookingId+ "_RegCard";
		String home = System.getProperty("user.home");
		File file = new File(home+"/Downloads/" + fileName + ".pdf");
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
       return output.toByteArray();
	}
}
