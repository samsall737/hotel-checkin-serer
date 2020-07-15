package com.nec.facerecognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.nec.exception.ExceptionHandlerApplication;

@SpringBootApplication(scanBasePackageClasses = {FaceRecognitionClientApplication.class, ExceptionHandlerApplication.class})
@PropertySource({"classpath:application.properties"})
	public class FaceRecognitionClientApplication extends SpringBootServletInitializer {

	    public FaceRecognitionClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(FaceRecognitionClientApplication.class, args);
	    }
}
