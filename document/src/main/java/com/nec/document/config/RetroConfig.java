package com.nec.document.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nec.document.client.DocumentClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class RetroConfig {
	

	@Bean
	public DocumentClient docClient(@Value("${connection.read.timeout.second:60}") final Long timeoutSeconds,
			@Value("${document.base.url}") final String baseUrl) {
		return new Retrofit.Builder()
				.client(new OkHttpClient.Builder().readTimeout(timeoutSeconds, TimeUnit.SECONDS)
						.connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
						.addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BASIC))
						.build())
				.baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create(buildDefaultMapper()))
				.addConverterFactory(ScalarsConverterFactory.create()).build()
				.create(DocumentClient.class);
	}
	
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
