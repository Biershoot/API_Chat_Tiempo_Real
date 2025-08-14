package com.alex.chat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // OpenAPI/Swagger endpoints
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // WebSocket endpoints
                .requestMatchers("/ws/**").permitAll()
                // Public API endpoints
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                // Read-only operations for chat messages can be public
                .requestMatchers(HttpMethod.GET, "/api/chat/messages/**").permitAll()
                // Write operations should be authenticated in production
                .requestMatchers(HttpMethod.POST, "/api/chat/messages/**").permitAll() // Cambiar a authenticated en producción
                .requestMatchers(HttpMethod.PUT, "/api/chat/messages/**").permitAll() // Cambiar a authenticated en producción
                .requestMatchers(HttpMethod.DELETE, "/api/chat/messages/**").permitAll() // Cambiar a authenticated en producción
                // H2 Console (solo para desarrollo)
                .requestMatchers("/h2-console/**").permitAll()
                // Regla por defecto
                .anyRequest().permitAll() // Cambiar a authenticated en producción
            )
            .headers(headers -> headers
                .frameOptions().disable() // Para la consola H2
                .xssProtection().disable() // Deshabilitar para APIs REST
            );

        return http.build();
    }
}
