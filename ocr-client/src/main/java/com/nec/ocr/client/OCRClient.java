package com.nec.ocr.client;


import com.nec.ocr.model.OCRResponse;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OCRClient {

	@POST("/api/document/data")
	Single<Response<OCRResponse>> ocrRequest(@Body RequestBody request);
}
