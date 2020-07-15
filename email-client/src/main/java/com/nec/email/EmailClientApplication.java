package com.nec.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackageClasses = {EmailClientApplication.class})
@PropertySource({"classpath:application.properties"})
public class EmailClientApplication extends SpringBootServletInitializer {

	    public EmailClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(EmailClientApplication.class, args);
	    }
	
}

