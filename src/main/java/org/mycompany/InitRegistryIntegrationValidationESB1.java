package org.mycompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class InitRegistryIntegrationValidationESB1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub{
        SpringApplication.run(InitRegistryIntegrationValidationESB1.class, args);
    

	}

}
