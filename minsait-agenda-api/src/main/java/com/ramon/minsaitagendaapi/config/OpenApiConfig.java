package com.ramon.minsaitagendaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Documentation")
                    .description("Minsait-Agenda-API")
                    .contact(new Contact().name("Ramon Silva").email("ramonsilva12305@outlook.com"))
                        .version("1.0.0"));

    }

}
