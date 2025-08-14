package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Publicador de mensajes en Redis.
 * Se encarga de enviar mensajes al canal compartido para
 * que todas las instancias de la aplicación los reciban.
 */
@Service
public class RedisPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Publica un mensaje en el canal especificado.
     *
     * @param channel Canal donde publicar el mensaje
     * @param message Mensaje u objeto a publicar
     */
    public void publish(String channel, Object message) {
        try {
            logger.debug("Publicando mensaje en canal '{}': {}", channel, message);
            redisTemplate.convertAndSend(channel, message);
            logger.debug("Mensaje publicado con éxito");
        } catch (Exception e) {
            logger.error("Error al publicar mensaje en Redis: {}", e.getMessage(), e);
            throw new RuntimeException("Error al publicar mensaje en Redis", e);
        }
    }
}
