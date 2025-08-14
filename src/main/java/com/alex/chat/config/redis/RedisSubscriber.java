package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Suscriptor de mensajes de Redis.
 * Recibe mensajes del canal de chat y los distribuye a
 * todos los clientes conectados por WebSocket.
 */
@Service
public class RedisSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructor que recibe las dependencias necesarias.
     */
    public RedisSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Método que se llama cuando llega un mensaje de Redis.
     *
     * @param message El mensaje recibido
     * @param channel El canal donde se recibió
     */
    public void onMessage(String message, String channel) {
        try {
            logger.debug("📩 Mensaje recibido desde Redis: {}", message);

            // Reenviar el mensaje a todos los clientes WebSocket conectados
            messagingTemplate.convertAndSend("/topic/messages", message);

            logger.debug("Mensaje reenviado a todos los clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
