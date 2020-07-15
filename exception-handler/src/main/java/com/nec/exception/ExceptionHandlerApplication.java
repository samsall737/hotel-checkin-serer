package com.nec.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackageClasses = {ExceptionHandlerApplication.class})
@PropertySource({"classpath:application.properties"})
public class ExceptionHandlerApplication extends SpringBootServletInitializer {

	    public ExceptionHandlerApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(ExceptionHandlerApplication.class, args);
	    }
	
}

