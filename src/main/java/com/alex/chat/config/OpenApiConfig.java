package com.alex.chat.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Chat en Tiempo Real")
                        .description("API RESTful para chat en tiempo real con soporte WebSocket")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alejandro")
                                .email("contacto@ejemplo.com")
                                .url("https://github.com/Biershoot"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.ejemplo.com")
                                .description("Servidor de producción")
                ));
    }
}
