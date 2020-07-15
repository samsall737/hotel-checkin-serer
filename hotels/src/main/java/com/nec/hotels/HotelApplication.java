package com.nec.hotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nec.document.DocumentApplication;
import com.nec.email.EmailClientApplication;
import com.nec.exception.ExceptionHandlerApplication;
import com.nec.facerecognition.FaceRecognitionClientApplication;
import com.nec.master.MasterClientApplication;
import com.nec.oauth.OAuthClientApplication;
import com.nec.ocr.OCRClientApplication;
import com.nec.pms.PMSClientApplication;
import com.nec.sms.SMSClientApplication;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.ConfigurableMapper;


@SpringBootApplication(scanBasePackageClasses = {HotelApplication.class, OAuthClientApplication.class,MasterClientApplication.class, 
		ExceptionHandlerApplication.class, SMSClientApplication.class, EmailClientApplication.class,
		PMSClientApplication.class,  OCRClientApplication.class,
		FaceRecognitionClientApplication.class, DocumentApplication.class})
@EnableEurekaClient
@EnableScheduling
@PropertySource({"classpath:application.properties"})
	public class HotelApplication extends SpringBootServletInitializer  {

	    public HotelApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(HotelApplication.class, args);
	    }
	    
	    @Bean
		public MapperFacade mapperFacade(){
			return new ConfigurableMapper();
		}
}
