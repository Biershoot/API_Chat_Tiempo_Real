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
     * Este método se llama automáticamente cuando llega un mensaje a Redis.
     * Recibe el mensaje y lo reenvía a todos los clientes WebSocket.
     *
     * @param message El mensaje recibido
     * @param channel El canal por el que llegó
     */
    public void onMessage(String message, String channel) {
        try {
            logger.debug("Mensaje recibido del canal '{}': {}", channel, message);

            // Enviar a todos los clientes conectados al topic de WebSocket
            messagingTemplate.convertAndSend("/topic/messages", message);

            logger.debug("Mensaje reenviado a todos los clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
