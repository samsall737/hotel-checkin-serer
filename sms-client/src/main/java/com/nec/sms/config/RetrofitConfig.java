package com.nec.sms.config;

import com.nec.sms.client.SmsClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofitConfig {


	@Bean
	public SmsClient smsClient(@Value("${connection.read.timeout.second:60}") final Long timeoutSeconds,
			@Value("${sms.base.url}") final String baseUrl) {
		return new Retrofit.Builder()
				.client(new OkHttpClient.Builder().readTimeout(timeoutSeconds, TimeUnit.SECONDS)
						.connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
						.addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BASIC)).build())
				.baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create()).build().create(SmsClient.class);
	}

}