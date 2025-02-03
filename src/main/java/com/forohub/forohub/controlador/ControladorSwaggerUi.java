package com.forohub.forohub.controlador;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Documentación ForoHub", description = "ForoHub es una API que hace parte de un Challenge propuesto por Alura Latam", version = "0.1.0"))
@SecurityRequirement(name = "BearerToken")
@SecurityScheme(name = "BearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SuppressWarnings("unused")
public class ControladorSwaggerUi {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("BearerToken")).components(new Components().addSecuritySchemes("BearerToken", new io.swagger.v3.oas.models.security.SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}