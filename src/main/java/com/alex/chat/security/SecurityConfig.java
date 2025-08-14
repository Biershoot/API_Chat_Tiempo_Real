package com.alex.chat.security;

import com.alex.chat.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // OpenAPI/Swagger endpoints
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Autenticación endpoints
                .requestMatchers("/api/auth/**").permitAll()
                // WebSocket endpoints - Ahora requerirán token en la conexión
                .requestMatchers("/ws/**").permitAll() // La validación se hará en WebSocketConfig
                // Public API endpoints
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                // Read-only operations for chat messages
                .requestMatchers(HttpMethod.GET, "/api/chat/messages/**").permitAll()
                // Write operations require authentication
                .requestMatchers(HttpMethod.POST, "/api/chat/messages/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/chat/messages/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/chat/messages/**").authenticated()
                // H2 Console (solo para desarrollo)
                .requestMatchers("/h2-console/**").permitAll()
                // Regla por defecto
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions().disable() // Para la consola H2
                .xssProtection().disable() // Deshabilitar para APIs REST
            );

        // Añadir filtro JWT antes del filtro de autenticación por usuario y contraseña
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
