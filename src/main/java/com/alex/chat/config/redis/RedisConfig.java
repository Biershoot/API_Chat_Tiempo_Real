package com.alex.chat.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuración de Redis para nuestro chat en tiempo real.
 * Aquí preparo todo lo necesario para que los mensajes puedan
 * viajar entre diferentes instancias de la aplicación.
 */
@Configuration
public class RedisConfig {

    /**
     * Define el canal donde vamos a intercambiar los mensajes.
     * Es como una frecuencia de radio a la que todos escuchamos.
     */
    @Bean
    public ChannelTopic chatTopic() {
        return new ChannelTopic("chat-channel");
    }

    /**
     * Configura el contenedor que escucha los mensajes de Redis.
     * Es como una radio que sintoniza nuestro canal y avisa cuando hay mensajes.
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
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
    public MessageListenerAdapter messageListenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    /**
     * Prepara un template para trabajar con Redis usando JSON.
     * Nos viene bien para enviar objetos complejos serializados.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Prepara un template para trabajar con Redis usando Strings.
     * Más simple, pero suficiente para mensajes de texto plano.
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
