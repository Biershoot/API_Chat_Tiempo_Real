package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * El mensajero que envía los mensajes a Redis.
 * Mi trabajo es tomar los mensajes y distribuirlos por el
 * canal de Redis para que lleguen a todas partes.
 */
@Service
public class RedisPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    /**
     * Me dan un redisTemplate para poder hacer mi trabajo,
     * y configuro el canal "chat" que vamos a usar siempre.
     */
    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.topic = new ChannelTopic("chat");
    }

    /**
     * Publica un mensaje en nuestro canal de chat.
     * Como un DJ pasando la canción que pidieron por la radio.
     */
    public void publish(String message) {
        try {
            logger.debug("Publicando mensaje en canal '{}': {}", topic.getTopic(), message);
            redisTemplate.convertAndSend(topic.getTopic(), message);
            logger.debug("Mensaje publicado con éxito");
        } catch (Exception e) {
            logger.error("Error al publicar mensaje en Redis: {}", e.getMessage(), e);
            throw new RuntimeException("Error al publicar mensaje en Redis", e);
        }
    }
}
