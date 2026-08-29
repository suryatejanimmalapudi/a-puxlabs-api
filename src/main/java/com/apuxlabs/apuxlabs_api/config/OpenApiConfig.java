package com.apuxlabs.apuxlabs_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apuxlabsOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("A-PuxLabs API")
                        .description(
                                "REST API documentation for A-PuxLabs registration "
                                        + "and medical examination services."
                        )
                        .version("1.0.0"));
    }
}