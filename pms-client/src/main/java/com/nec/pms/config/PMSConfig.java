package com.nec.pms.config;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.nec.pms.utils.PropertiesCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nec.master.gateway.MasterGateway;
import com.nec.master.model.Hotel;
import com.nec.pms.client.PMSClient;
import com.nec.pms.gateway.PMSGateway;
import com.nec.pms.gateway.PMSTokenGateway;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Interceptor.Chain;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class PMSConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(PMSConfig.class);

	@Autowired
	private PMSTokenGateway pmsTokenGateway;
	
	@Autowired
	MasterGateway masterGateway;
	
	
	
	@Bean
	public PMSClient pmsClient(@Value("${connection.read.timeout.second:60}") final Long timeoutSeconds,
			@Value("${pms.base.url}") final String baseUrl) {
		return new Retrofit.Builder()
				.client(okHttpClient)
				.baseUrl(baseUrl).addConverterFactory(JacksonConverterFactory.create(buildDefaultMapper()))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(PMSClient.class);
	}

	
	OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.addInterceptor(new Interceptor() {
				
				@Override
				public Response intercept(Chain chain) throws IOException {
					UUID hotelId = null;
					if(Objects.nonNull(MDC.get("hotel_id"))){
					  hotelId = UUID.fromString( MDC.get("hotel_id"));
					}
					Hotel hotel = masterGateway.getHotelById(hotelId);
					String accessToken = hotel.getConfiguration().getToken();
					if(Objects.isNull(accessToken)) {
						accessToken = pmsTokenGateway.getToken(hotelId).getToken();
						if(Objects.nonNull(accessToken)) {
							try {
								
						masterGateway.saveAccessToken(accessToken, hotelId);
							}
							catch(Exception e) {
								logger.error(e.getMessage());
							}
						}
					}
					Request request = chain.request().newBuilder().addHeader("x-access-token", accessToken).build();
					logger.info("============= request ============{} ",request);
					Response response = chain.proceed(request);
					logger.info("============= response ============{} ",response);
					if(response.code() == HttpStatus.UNAUTHORIZED.value()) {
						if(Objects.isNull(response.header("x-access-refresh-token"))) {
						response.body().close();
						Response r = null;
						r = makeToken(request, chain, 0, hotelId);
						return r;
					}
						else {
							 Response refreshResponse = chain.proceed(request);
							 if(refreshResponse.code() == HttpStatus.UNAUTHORIZED.value()) {
								refreshResponse.body().close();
								Response r = null;
								r = makeToken(request, chain, 0, hotelId);
								return r;
							 }
							 return refreshResponse;
						}
						}
					return response;
				}

				private Response makeToken(Request req, Chain chain, int count, UUID hotelId) throws IOException {
											
					if(count>3) {
						Response response = chain.proceed(req);
						return response;
					}
					String token = pmsTokenGateway.getToken(hotelId).getToken();
					logger.info("============= token ============{} ",token);
					try {
					masterGateway.saveAccessToken(token, hotelId);
					}
					catch(Exception e) {
						logger.error(e.getMessage());
					}
					Request newRequest;
					newRequest = req.newBuilder().header("x-access-token", token).build();
					Response another = chain.proceed(newRequest);
					logger.info("============= response ============{} ", another);
					if(another.code()!= HttpStatus.OK.value()&& count<3) {
						count++;
						makeToken(newRequest, chain, count, hotelId);
					}
					return another;
					
				}
			})
			.build();

	
	
	/**
	 * Builds the default object mapper to be used by clients to parse/populate json
	 * object.
	 * 
	 * @return the {@link ObjectMapper} prepared with default configuration.
	 */
	private ObjectMapper buildDefaultMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}

}
