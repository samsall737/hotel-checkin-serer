package com.nec.ocr.gateway;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import com.nec.exception.OCRException;
import com.nec.ocr.client.OCRClient;
import com.nec.ocr.model.OCRResponse;

import retrofit2.Response;

@Component
public class OCRGateway {

	@Autowired
	private OCRClient oCRClient;
	
	public OCRResponse ocrRequest(RequestBody request) {
		Response<OCRResponse> response = oCRClient.ocrRequest(request).blockingGet();
		if(Objects.isNull(response)) {
			throw new OCRException("OCR Exception, Unable to connect OCR Server", HttpStatus.BAD_REQUEST);
		}
		if(response.body().getStatus().equalsIgnoreCase("fail")) {
			throw new OCRException("OCR Exception, Invalid Document" + response.body().getMsg(), HttpStatus.BAD_REQUEST);
		}
		return response.body();
	}
	
}
