package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * El mensajero que envía los mensajes a Redis.
 * Mi trabajo es tomar los mensajes y distribuirlos por el
 * canal de Redis para que lleguen a todas partes.
 */
@Service
public class RedisPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final StringRedisTemplate redisTemplate;

    /**
     * Me dan un redisTemplate para poder hacer mi trabajo,
     * lo guardo para usarlo cada vez que alguien quiera publicar.
     */
    public RedisPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Publica un mensaje en el canal que me digan.
     * Como un DJ pasando la canción que pidieron por la radio.
     */
    public void publish(String channel, String message) {
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
