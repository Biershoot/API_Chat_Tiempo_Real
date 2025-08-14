package com.alex.chat.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Configura el contenedor que maneja las suscripciones a canales de Redis.
 * Define los canales y el contenedor de listeners para mensajes.
 */
@Configuration
public class RedisSubscriberConfig {

    /**
     * Define el canal principal para comunicación del chat.
     */
    @Bean
    public PatternTopic chatTopic() {
        return new PatternTopic("chat");
    }

    /**
     * Registra el suscriptor en el canal de chat.
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            RedisMessageSubscriber subscriber) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, chatTopic());
        return container;
    }
}
