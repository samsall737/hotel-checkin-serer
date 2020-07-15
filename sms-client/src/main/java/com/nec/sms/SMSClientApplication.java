package com.nec.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(scanBasePackageClasses = {SMSClientApplication.class})
@PropertySource({"classpath:application.properties"})
	public class SMSClientApplication extends SpringBootServletInitializer {

	    public SMSClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(SMSClientApplication.class, args);
	    }
}
