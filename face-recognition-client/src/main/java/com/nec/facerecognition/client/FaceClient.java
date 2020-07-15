package com.nec.facerecognition.client;


import io.reactivex.Single;
import org.springframework.stereotype.Component;

import com.nec.facerecognition.model.request.FaceRecogniseRequest;
import com.nec.facerecognition.model.request.RegisterFaceDTO;
import com.nec.facerecognition.model.response.FaceRegisterResponse;
import com.nec.facerecognition.model.response.RecogniseRes;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Component
public interface FaceClient {
	
	@POST("/KaoatoWeb/api/registRegistrantWithFace")
	Single<Response<FaceRegisterResponse>> registerFace(@Body final RegisterFaceDTO request);
	
	@POST("/KaoatoWeb/api/authenticate")
	Single<Response<RecogniseRes>> recogniseFace(@Body FaceRecogniseRequest request);
	
	
}
