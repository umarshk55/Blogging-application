package com.shaik.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class OpenApiConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name(AUTHORIZATION_HEADER);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("JWT"))
            .components(new Components().addSecuritySchemes("JWT", createAPIKeyScheme()))
            .info(new Info()
                .title("Blogging Application : Backend Course")
                .description("This project is developed by Umar")
                .termsOfService("urn:tos")
                .contact(new Contact()
                    .name("Umar Shaik")
                    .email("umrshq@gmail.com")
                    .url("https://www.linkedin.com/in/umarshaik00/"))
                .license(new License()
                    .name("License of Api")
                    .url("http://www.apache.org/licenses/LICENSE-2.0"))
                .version("1.0"));
    }
}
