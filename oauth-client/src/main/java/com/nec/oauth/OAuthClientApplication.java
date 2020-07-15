package com.nec.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.nec.exception.ExceptionHandlerApplication;

@SpringBootApplication(scanBasePackageClasses = {OAuthClientApplication.class, ExceptionHandlerApplication.class})
@PropertySource({"classpath:application.properties"})
	public class OAuthClientApplication extends SpringBootServletInitializer {

	    public OAuthClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(OAuthClientApplication.class, args);
	    }
}
