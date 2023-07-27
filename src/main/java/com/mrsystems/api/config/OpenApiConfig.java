package com.mrsystems.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI custonOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API RESTFULL com Java 17")
                        .version("v1")
                        .description("API de gerenciamento de pessoas")
                        .termsOfService("https://mrsystems.com.br/api")
                        .license(new License()
                            .name("Apache 2.0")
                            .url("https://mrsystems.com.br/api")
                        )
                );
    }
}
