package com.nec.sms.gateway;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nec.sms.client.SmsClient;

@Component
public class SmsGateway {

	@Autowired
	SmsClient smsClient;

	public String smsRequest(Map<String, String> sms) {
		return smsClient.smsRequest(sms).blockingGet();
	}

}
