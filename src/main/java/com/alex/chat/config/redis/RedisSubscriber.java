package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * El receptor de mensajes de Redis.
 * Estoy todo el día escuchando lo que viene por el canal
 * y se lo paso a los clientes que están conectados por WebSocket.
 */
@Service
public class RedisSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Necesito el template de mensajería para poder enviar
     * los mensajes que recibo a los clientes WebSocket.
     */
    public RedisSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Este método se llama automáticamente cuando llega algo al canal.
     * Como cuando recibes un mensaje y lo reenvías al grupo de WhatsApp.
     */
    public void onMessage(String message, String channel) {
        try {
            logger.debug("Mensaje recibido del canal '{}': {}", channel, message);
            messagingTemplate.convertAndSend("/topic/messages", message);
            logger.debug("Mensaje reenviado a clientes WebSocket");
        } catch (Exception e) {
            logger.error("Error al procesar mensaje de Redis: {}", e.getMessage(), e);
        }
    }
}
