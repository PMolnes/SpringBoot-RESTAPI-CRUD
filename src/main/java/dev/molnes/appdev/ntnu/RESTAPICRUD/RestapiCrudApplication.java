package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@SpringBootApplication
public class RestapiCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiCrudApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		// Return a prepared Docket instance
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("dev.molnes.appdev.ntnu.RESTAPICRUD"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo (
				"Spring Boot REST API with CRUD operations",
				"This is a solution for how to generate Swagger documentation for a Spring Boot project",
				"1.0",
				"https://github.com/PMolnes/SpringBoot-RESTAPI-CRUD",
				new Contact("Petter Molnes", "https://github.com/PMolnes", null),
				null,
				null,
				Collections.emptyList());
	}
}
