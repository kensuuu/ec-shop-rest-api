package com.kensuuu.ecshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class SwaggerConfig {
    private final String title = "ECShop API";
    private final String description = "ECShop API Documentation";
    private final String version = "v1";
    private final String securityShemaName = "Bearer";

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securityShemaName))
                .components(new Components().addSecuritySchemes(securityShemaName,
                        new SecurityScheme().name(securityShemaName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title(title).description(description).version(version));
    }
}
