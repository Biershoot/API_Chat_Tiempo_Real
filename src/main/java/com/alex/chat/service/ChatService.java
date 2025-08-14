package com.alex.chat.service;

import com.alex.chat.config.redis.RedisPublisher;
import com.alex.chat.dto.ChatMessage;
import com.alex.chat.message.entity.Message;
import com.alex.chat.message.repo.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio central para el manejo del chat.
 * Me encargo de toda la lógica del chat, tanto para los mensajes en tiempo real
 * como para el almacenamiento en base de datos.
 */
@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final MessageRepository messageRepository;
    private final RedisPublisher redisPublisher;

    /**
     * Constructor donde recibo todas las dependencias que necesito.
     * El repositorio para guardar los mensajes, el publisher para Redis.
     */
    @Autowired
    public ChatService(MessageRepository messageRepository,
                       RedisPublisher redisPublisher) {
        this.messageRepository = messageRepository;
        this.redisPublisher = redisPublisher;
    }

    /**
     * Cuando llega un mensaje por WebSocket, lo preparo y lo distribuyo a todos.
     * Le pongo la hora actual y lo envío a través de Redis para que llegue
     * a todas las instancias de la aplicación.
     */
    public void processAndDistributeMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        try {
            // Formato simplificado como en los requisitos: "remitente: contenido"
            String formattedMessage = message.getSender() + ": " + message.getContent();
            logger.debug("Distribuyendo mensaje: {}", formattedMessage);
            redisPublisher.publish("chat", formattedMessage);
        } catch (Exception e) {
            logger.error("Error al procesar el mensaje para Redis: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el mensaje para Redis", e);
        }
    }

    /**
     * Devuelve todos los mensajes que tenemos guardados.
     * Útil para cargar el historial al entrar al chat.
     * Usa caché para mejorar el rendimiento con muchos usuarios.
     */
    @Cacheable(value = "messages", key = "'all'")
    @Transactional(readOnly = true)
    public List<Message> getAllMessages() {
        logger.debug("Obteniendo todos los mensajes (caché miss)");
        return messageRepository.findAll();
    }

    /**
     * Busca un mensaje concreto por su ID.
     * Si existe lo devuelve, si no... pues nada.
     * Usa caché para evitar consultas repetidas a la base de datos.
     */
    @Cacheable(value = "messages", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Message> getMessageById(Long id) {
        logger.debug("Buscando mensaje con ID: {} (caché miss)", id);
        return messageRepository.findById(id);
    }

    /**
     * Guarda un mensaje nuevo en la base de datos.
     * Le añado la fecha y hora actual antes de guardarlo.
     * Al guardar un nuevo mensaje, invalida la caché de todos los mensajes.
     */
    @CacheEvict(value = "messages", key = "'all'")
    @Transactional
    public Message saveMessage(Message message) {
        message.setSentAt(LocalDateTime.now());
        logger.debug("Guardando mensaje: {}", message);
        Message savedMessage = messageRepository.save(message);

        // Publicar el mensaje guardado en Redis para que todas las instancias se enteren
        try {
            String notification = "NEW_MESSAGE:" + savedMessage.getId();
            redisPublisher.publish("chat-notifications", notification);
        } catch (Exception e) {
            logger.warn("No se pudo notificar el nuevo mensaje: {}", e.getMessage());
        }

        return savedMessage;
    }

    /**
     * Borra un mensaje si existe.
     * Devuelve OK si lo encontré y borré, o Not Found si no existía.
     * Al borrar un mensaje, invalida tanto la caché de ese mensaje como la de todos.
     */
    @CacheEvict(value = "messages", allEntries = true)
    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteMessage(Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    messageRepository.delete(message);
                    logger.debug("Mensaje con ID {} eliminado", id);
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElseGet(() -> {
                    logger.warn("Intento de eliminar mensaje inexistente con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}
