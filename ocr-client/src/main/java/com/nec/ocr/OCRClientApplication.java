package com.nec.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.nec.exception.ExceptionHandlerApplication;



@SpringBootApplication(scanBasePackageClasses = {OCRClientApplication.class, ExceptionHandlerApplication.class})
@PropertySource({"classpath:application.properties"})
	public class OCRClientApplication extends SpringBootServletInitializer {

	    public OCRClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(OCRClientApplication.class, args);
	    }
}

