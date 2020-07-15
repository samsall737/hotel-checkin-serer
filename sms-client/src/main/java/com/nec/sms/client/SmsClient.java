package com.nec.sms.client;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface SmsClient {
	
	@POST("/OTP_ACL_Web/OtpRequestListener")
	Single<String> smsRequest(@QueryMap Map<String, String> sms);
}
