package com.nec.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackageClasses = {DocumentApplication.class})
@PropertySource({"classpath:application.properties"})
public class DocumentApplication extends SpringBootServletInitializer {

	    public DocumentApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(DocumentApplication.class, args);
	    }
	
}
