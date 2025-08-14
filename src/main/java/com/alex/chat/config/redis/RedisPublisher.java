package com.alex.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * El publicador de mensajes en Redis.
 * Se encarga de enviar mensajes al canal compartido para
 * que todas las instancias de la aplicación los reciban.
 */
@Service
public class RedisPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    /**
     * Constructor que recibe las dependencias necesarias.
     */
    public RedisPublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    /**
     * Publica un mensaje en el canal de chat.
     *
     * @param message El mensaje a publicar
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

    /**
     * Publica un mensaje en un canal específico.
     *
     * @param channel El canal donde publicar
     * @param message El mensaje u objeto a publicar
     */
    public void publish(String channel, Object message) {
        try {
            logger.debug("Publicando objeto en canal '{}': {}", channel, message);
            redisTemplate.convertAndSend(channel, message);
            logger.debug("Objeto publicado con éxito en canal personalizado");
        } catch (Exception e) {
            logger.error("Error al publicar en canal {}: {}", channel, e.getMessage(), e);
            throw new RuntimeException("Error al publicar en Redis", e);
        }
    }
}
