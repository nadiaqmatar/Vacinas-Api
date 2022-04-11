package com.github.nadia.vacinasapi.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

//classe de configuração do open-ui (swagger)
@OpenAPIDefinition(info =
@Info(title = "Vacina Api",
        version = "v1",
        description = "Aplicação feita para cadastrar usuarios e vacinas," +
                " e permitir consultas no banco de dados"))
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("Vacinas-API")
                                .version("v1")
                                .license( new License().
                                        name("Apache 2.0").
                                        url("http://springdoc.org")
                                )
                );
    }
}