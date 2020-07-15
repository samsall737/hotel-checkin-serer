package com.nec.facerecognition.gateway;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.springframework.stereotype.Component;

import com.nec.exception.HotelCheckinException;
import com.nec.facerecognition.client.FaceClient;
import com.nec.facerecognition.model.request.FaceRecogniseRequest;
import com.nec.facerecognition.model.request.RegisterFaceDTO;
import com.nec.facerecognition.model.response.FaceRegisterResponse;
import com.nec.facerecognition.model.response.RecogniseRes;

import retrofit2.Response;
import retrofit2.http.Body;

@Component
public class FaceGateway {

	@Autowired
	private FaceClient faceClient;

	Logger LOGGER = LoggerFactory.getLogger(FaceGateway.class);

	/**
	 * 
	 * @param registerFace
	 * @return
	 */
	public boolean registerFace(RegisterFaceDTO registerFace) {
		LOGGER.info("Register Face request {} :", registerFace);
		final Response<FaceRegisterResponse> response = faceClient.registerFace(registerFace).blockingGet();
		LOGGER.info("Register Face {} res:", response);
		if (Objects.nonNull(response.errorBody()) || response.code() == HttpStatus.NO_CONTENT.value()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public RecogniseRes recogniseFace(@Body FaceRecogniseRequest request) {
		LOGGER.info("Recognise Face request {} :", request);
		Response<RecogniseRes> response = faceClient.recogniseFace(request).blockingGet();
		LOGGER.info("Recognise Face {} res:", response);
		if (Objects.isNull(response.body()) || response.code() == HttpStatus.NO_CONTENT.value()) {
			LOGGER.info("Recognise Face {} code: ", response.code());
			return null;
			//throw new HotelCheckinException("No Face in the Image, SFA response code " + response.code(), HttpStatus.BAD_REQUEST);
		}
		return response.body();
	}

}
