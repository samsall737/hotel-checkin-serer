package com.nec.pms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.nec.exception.ExceptionHandlerApplication;



@SpringBootApplication(scanBasePackageClasses = {PMSClientApplication.class, ExceptionHandlerApplication.class})
@PropertySource({"classpath:application.properties"})
	public class PMSClientApplication extends SpringBootServletInitializer {

	    public PMSClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(PMSClientApplication.class, args);
	    }
}
