package com.nec.master;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


//@SpringBootApplication(scanBasePackageClasses = {MasterClientApplication.class, ExceptionHandlerApplication.class})

@SpringBootApplication(scanBasePackages =  {"com.nec.master", "com.nec.exception"})
@PropertySource({"classpath:application.properties"})
	public class MasterClientApplication extends SpringBootServletInitializer {

	    public MasterClientApplication() {
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(MasterClientApplication.class, args);
	    }
}

