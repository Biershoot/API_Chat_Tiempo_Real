package com.alex.chat.service;

import com.alex.chat.config.redis.RedisPublisher;
import com.alex.chat.dto.ChatMessage;
import com.alex.chat.message.entity.Message;
import com.alex.chat.message.repo.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String CHAT_CHANNEL = "chat-channel";

    private final MessageRepository messageRepository;
    private final RedisPublisher redisPublisher;
    private final ObjectMapper objectMapper;

    /**
     * Constructor donde recibo todas las dependencias que necesito.
     * El repositorio para guardar los mensajes, el publisher para Redis
     * y el mapper para convertir entre Java y JSON.
     */
    @Autowired
    public ChatService(MessageRepository messageRepository,
                       RedisPublisher redisPublisher,
                       ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.redisPublisher = redisPublisher;
        this.objectMapper = objectMapper;
    }

    /**
     * Cuando llega un mensaje por WebSocket, lo preparo y lo distribuyo a todos.
     * Le pongo la hora actual y lo envío a través de Redis para que llegue
     * a todas las instancias de la aplicación.
     */
    public void processAndDistributeMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            logger.debug("Distribuyendo mensaje: {}", jsonMessage);
            redisPublisher.publish(CHAT_CHANNEL, jsonMessage);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir mensaje a JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el mensaje para Redis", e);
        }
    }

    /**
     * Devuelve todos los mensajes que tenemos guardados.
     * Útil para cargar el historial al entrar al chat.
     */
    @Transactional(readOnly = true)
    public List<Message> getAllMessages() {
        logger.debug("Obteniendo todos los mensajes");
        return messageRepository.findAll();
    }

    /**
     * Busca un mensaje concreto por su ID.
     * Si existe lo devuelve, si no... pues nada.
     */
    @Transactional(readOnly = true)
    public Optional<Message> getMessageById(Long id) {
        logger.debug("Buscando mensaje con ID: {}", id);
        return messageRepository.findById(id);
    }

    /**
     * Guarda un mensaje nuevo en la base de datos.
     * Le añado la fecha y hora actual antes de guardarlo.
     */
    @Transactional
    public Message saveMessage(Message message) {
        message.setSentAt(LocalDateTime.now());
        logger.debug("Guardando mensaje: {}", message);
        return messageRepository.save(message);
    }

    /**
     * Borra un mensaje si existe.
     * Devuelve OK si lo encontré y borré, o Not Found si no existía.
     */
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
