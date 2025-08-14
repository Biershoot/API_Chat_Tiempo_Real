package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Suscriptor de mensajes de Redis.
 * Recibe mensajes del canal de chat y los distribuye a
 * todos los clientes conectados por WebSocket.
 */
@Service
public class RedisSubscriber implements MessageListener {

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
     * Implementación de la interfaz MessageListener.
     *
     * @param message El mensaje recibido
     * @param pattern El patrón del canal
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channelPattern = new String(pattern);
            String messageContent = new String(message.getBody());

            logger.debug("Mensaje recibido de Redis: {}", messageContent);

            // Enviar a todos los clientes conectados al topic de WebSocket
            messagingTemplate.convertAndSend("/topic/messages", messageContent);

            logger.debug("Mensaje de Redis reenviado a todos los clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }

    /**
     * Método alternativo para recibir mensajes como String.
     * Este método puede ser llamado por el MessageListenerAdapter.
     *
     * @param message El mensaje recibido como String
     * @param channel El canal como String
     */
    public void onMessage(String message, String channel) {
        try {
            logger.debug("String mensaje recibido del canal '{}': {}", channel, message);

            // Enviar a todos los clientes conectados al topic de WebSocket
            messagingTemplate.convertAndSend("/topic/messages", message);

            logger.debug("String mensaje reenviado a todos los clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar String mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
