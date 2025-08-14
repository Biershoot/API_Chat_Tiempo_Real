package com.alex.chat.controller;

import com.alex.chat.config.redis.RedisPublisher;
import com.alex.chat.dto.ChatMessage;
import com.alex.chat.message.entity.Message;
import com.alex.chat.message.repo.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    @MessageMapping("/sendMessage") // El cliente envía aquí
    public void sendMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        try {
            // Convertir el mensaje a JSON y publicarlo en Redis
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisPublisher.publish("chat-channel", jsonMessage);
        } catch (JsonProcessingException e) {
            // Manejar la excepción apropiadamente
            throw new RuntimeException("Error al procesar el mensaje para Redis", e);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageRepository.findAll());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        message.setSentAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMessage(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    messageRepository.delete(message);
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
