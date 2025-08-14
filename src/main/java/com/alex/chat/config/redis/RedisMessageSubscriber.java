package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Componente que escucha mensajes en canales de Redis.
 * Recibe mensajes de otras instancias y los distribuye a los clientes WebSocket conectados.
 */
@Component
public class RedisMessageSubscriber implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);
    private final SimpMessagingTemplate messagingTemplate;

    public RedisMessageSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Método invocado cuando se recibe un mensaje en un canal suscrito.
     *
     * @param message Mensaje recibido
     * @param pattern Patrón del canal
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageContent = new String(message.getBody());
            String channelPattern = new String(pattern);

            logger.debug("Mensaje recibido desde Redis en canal '{}': {}", channelPattern, messageContent);

            // Reenviar el mensaje a todos los clientes WebSocket conectados
            messagingTemplate.convertAndSend("/topic/messages", messageContent);

            logger.debug("Mensaje reenviado a clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
