package com.alex.chat.websocket;

import com.alex.chat.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    private final JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new JwtHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * Interceptor que valida el token JWT durante el establecimiento de la conexión WebSocket.
     */
    private class JwtHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) {
            try {
                if (request instanceof ServletServerHttpRequest) {
                    HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                    String token = servletRequest.getParameter("token");

                    logger.debug("Interceptando conexión WebSocket. Token: {}", token != null ? "[PRESENTE]" : "[AUSENTE]");

                    if (token != null && jwtUtil.validateToken(token)) {
                        String username = jwtUtil.extractUsername(token);
                        logger.debug("Token válido para usuario: {}", username);

                        // Almacenar información del usuario en los atributos de la sesión WebSocket
                        attributes.put("username", username);
                        return true;
                    } else {
                        logger.warn("Intento de conexión WebSocket con token inválido o ausente");
                        return false;
                    }
                }

                return false;
            } catch (Exception e) {
                logger.error("Error durante la validación del token en handshake WebSocket: {}", e.getMessage());
                return false;
            }
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
            // No es necesario implementar nada aquí
        }
    }
}
