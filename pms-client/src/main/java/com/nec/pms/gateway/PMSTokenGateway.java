package com.nec.pms.gateway;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.nec.exception.PMSException;
import com.nec.master.gateway.MasterGateway;
import com.nec.master.model.Hotel;
import com.nec.pms.client.PMSTokenClient;
import com.nec.pms.model.PMSToken;
import com.nec.pms.model.PMSTokenRequest;
import com.nec.pms.model.PMSTokenResponse;

import retrofit2.Response;

@Component
public class PMSTokenGateway {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PMSTokenGateway.class);
	private final PMSTokenClient tokenClient;
	private final MasterGateway masterGateway;

	@Autowired
	public PMSTokenGateway(PMSTokenClient tokenClient,MasterGateway masterGateway) {
		this.masterGateway = masterGateway;
		this.tokenClient = tokenClient;
	}
	
	@Value("${pms.token.url}")
	private String url;
	
	
	public PMSToken getToken(UUID hotelId) {
		Hotel hotel = masterGateway.getHotelById(hotelId);
		if(Objects.nonNull(hotel) && Objects.nonNull(hotel.getConfiguration())) {
		com.nec.master.model.Configuration configuration = hotel.getConfiguration();
		PMSTokenRequest tokenRequest = new PMSTokenRequest(
				configuration.getPmsId(), configuration.getPassword(), configuration.getSource(), 
						configuration.getSystemType(), hotel.getHotelCode(), hotel.getChains().getChainCode(), configuration.getUser()
						, configuration.getPmsHost() );
		Response<PMSTokenResponse> response = tokenClient.getToken(url,tokenRequest).blockingGet();
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
				try {
					errorBody = response.errorBody().string();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
				LOGGER.error(errorBody);
				throw new PMSException("PMS Exception", HttpStatus.valueOf(response.code()));
			}
		return response.body().getData();
		}
		return null;
	}

}
