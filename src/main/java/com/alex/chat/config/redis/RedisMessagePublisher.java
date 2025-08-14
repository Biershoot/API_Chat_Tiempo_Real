package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio para publicar mensajes en canales de Redis.
 * Permite la comunicación entre diferentes instancias de la aplicación.
 */
@Service
public class RedisMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessagePublisher.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
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
