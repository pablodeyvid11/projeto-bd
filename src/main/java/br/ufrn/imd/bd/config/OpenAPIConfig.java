package br.ufrn.imd.bd.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Value("${festivalle.dev-url}")
	private String devUrl;

	@Value("${festivalle.prod-url}")
	private String prodUrl;

	@Bean
	OpenAPI festivalleAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("suport@festivalle.com");
		contact.setName("Festivalle");
		contact.setUrl("https://www.festivalle.com");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("Festivalle API").version("1.0").contact(contact)
				.description("This API exposes endpoints to manage the Festivalle ecosystem.")
				.termsOfService("https://www.festivalle.com/terms").license(mitLicense);

		SecurityScheme securityScheme = new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
				.in(In.HEADER).name("Authorization");

		OpenAPI openAPI = new OpenAPI().info(info).addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth", securityScheme))
				.servers(List.of(devServer, prodServer));

		Paths paths = openAPI.getPaths();
		if (paths != null) {
			paths.forEach((key, pathItem) -> {
				if (key.equals("/signin") || key.equals("/signup")) {
					pathItem.readOperations().forEach(operation -> removeSecurity(operation));
				} else {
					pathItem.readOperations().forEach(operation -> addSecurity(operation));
				}
			});
		}

		return openAPI;
	}

	private void removeSecurity(Operation operation) {
		operation.setSecurity(List.of());
	}

	private void addSecurity(Operation operation) {
		operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}
