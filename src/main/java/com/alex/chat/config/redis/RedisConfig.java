package com.alex.chat.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuración de Redis para nuestro chat en tiempo real.
 * Aquí preparo todo lo necesario para que los mensajes puedan
 * viajar entre diferentes instancias de la aplicación.
 */
@Configuration
public class RedisConfig {

    /**
     * Prepara un template para trabajar con Redis usando JSON.
     * Nos viene bien para enviar objetos complejos serializados.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    /**
     * Define el canal donde vamos a intercambiar los mensajes.
     * Es como una frecuencia de radio a la que todos escuchamos.
     */
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("chat");
    }

    /**
     * Configura el contenedor que escucha los mensajes de Redis.
     * Es como una radio que sintoniza nuestro canal y avisa cuando hay mensajes.
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory,
                                                      MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listenerAdapter, topic());
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
