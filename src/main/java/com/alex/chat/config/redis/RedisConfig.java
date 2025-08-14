package com.alex.chat.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuración de Redis para nuestro chat en tiempo real.
 * Aquí preparo todo lo necesario para que los mensajes puedan
 * viajar entre diferentes instancias de la aplicación.
 */
@Configuration
public class RedisConfig {

    /**
     * Configura la conexión a Redis usando Lettuce.
     * Lettuce es un cliente de Redis non-blocking y thread-safe.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    /**
     * Prepara un template para trabajar con Redis.
     * Este template nos permite enviar y recibir mensajes.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Define el canal donde vamos a intercambiar los mensajes.
     * Es como una frecuencia de radio a la que todos escuchamos.
     */
    @Bean
    public ChannelTopic chatTopic() {
        return new ChannelTopic("chat");
    }

    /**
     * Configura el contenedor que escucha los mensajes de Redis.
     * Es como una radio que sintoniza nuestro canal y avisa cuando hay mensajes.
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                      MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, chatTopic());
        return container;
    }

    /**
     * Configura el adaptador que conecta Redis con nuestro código.
     * Básicamente le dice a Redis: "cuando llegue un mensaje, llama a este método".
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }
}
