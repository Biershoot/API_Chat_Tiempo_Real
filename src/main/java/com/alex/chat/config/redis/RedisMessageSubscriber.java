package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Suscriptor de mensajes de Redis.
 * Recibe mensajes del canal de chat y los distribuye a
 * todos los clientes conectados por WebSocket.
 */
@Service
public class RedisMessageSubscriber implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

    /**
     * Constructor que recibe las dependencias necesarias.
     */
    public RedisMessageSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Método que se llama cuando llega un mensaje de Redis.
     * Implementación de la interfaz MessageListener.
     *
     * @param message El mensaje recibido
     * @param pattern El patrón del canal
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            Object msg = serializer.deserialize(message.getBody());
            logger.debug("📩 Mensaje recibido desde Redis: {}", msg);

            // Reenviar el mensaje a todos los clientes WebSocket conectados
            messagingTemplate.convertAndSend("/topic/messages", msg);

            logger.debug("Mensaje reenviado a todos los clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
