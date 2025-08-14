package com.alex.chat.controller;

import com.alex.chat.config.redis.RedisPublisher;
import com.alex.chat.dto.ChatMessage;
import com.alex.chat.message.entity.Message;
import com.alex.chat.service.ChatMetricsService;
import com.alex.chat.service.ChatService;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;

/**
 * Controlador para nuestra API de chat.
 * Aquí manejo todas las peticiones que tienen que ver con mensajes,
 * tanto las normales por HTTP como las de WebSocket.
 */
@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat API", description = "API para gestionar mensajes de chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;
    private final RedisPublisher redisPublisher;
    private final ChatMetricsService metricsService;

    @Autowired
    public ChatController(ChatService chatService, RedisPublisher redisPublisher, ChatMetricsService metricsService) {
        this.chatService = chatService;
        this.redisPublisher = redisPublisher;
        this.metricsService = metricsService;
    }

    /**
     * Endpoint para recibir mensajes por WebSocket.
     * Cuando alguien manda un mensaje, lo recibo y lo publico en Redis
     * para que llegue a todas las instancias de la aplicación.
     */
    @MessageMapping("/sendMessage")
    public void sendMessage(@Validated ChatMessage message) {
        logger.debug("Mensaje WebSocket recibido: {}", message);

        // Iniciar medición de tiempo de procesamiento
        Timer.Sample sample = metricsService.startMessageProcessingTimer();

        try {
            // Formatear el mensaje con remitente
            String formattedMessage = message.getSender() + ": " + message.getContent();

            // Publicar el mensaje en el canal de chat de Redis
            // Esto distribuirá el mensaje a todas las instancias de la aplicación
            redisPublisher.publish("chat", formattedMessage);

            // Incrementar contador de mensajes enviados
            metricsService.incrementMessageCount();

            // No necesitamos enviar el mensaje directamente a los clientes WebSocket
            // porque el RedisSubscriber ya se encarga de eso cuando recibe
            // el mensaje desde Redis
        } finally {
            // Finalizar medición de tiempo de procesamiento
            metricsService.timeMessageProcessing(sample, null);
        }
    }

    /**
     * Maneja eventos de conexión de WebSocket.
     * Registra métricas cuando un usuario se conecta.
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        logger.debug("Usuario conectado: {}", headers.getSessionId());
        metricsService.recordUserConnection();
    }

    /**
     * Maneja eventos de desconexión de WebSocket.
     * Registra métricas cuando un usuario se desconecta.
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        logger.debug("Usuario desconectado: {}", headers.getSessionId());
        metricsService.recordUserDisconnection();
    }

    /**
     * Endpoint para obtener todos los mensajes guardados.
     * Útil para cargar el historial cuando alguien entra al chat.
     */
    @GetMapping("/messages")
    @Operation(
        summary = "Obtener todos los mensajes",
        description = "Recupera todos los mensajes guardados en la base de datos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Mensajes recuperados correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Message.class))
            )
        }
    )
    public ResponseEntity<List<Message>> getAllMessages() {
        logger.debug("Solicitud para obtener todos los mensajes");
        return ResponseEntity.ok(chatService.getAllMessages());
    }

    /**
     * Endpoint para buscar un mensaje específico por su ID.
     * Si lo encuentra lo devuelve, si no, devuelve un 404.
     */
    @GetMapping("/messages/{id}")
    @Operation(
        summary = "Obtener mensaje por ID",
        description = "Busca un mensaje específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
        }
    )
    public ResponseEntity<Message> getMessageById(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable Long id) {
        logger.debug("Solicitud para obtener mensaje con ID: {}", id);
        return chatService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Mensaje con ID {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Endpoint para crear un nuevo mensaje.
     * Recibe los datos del mensaje, lo guarda y devuelve el resultado.
     */
    @PostMapping("/messages")
    @Operation(
        summary = "Crear mensaje",
        description = "Guarda un nuevo mensaje en la base de datos",
        responses = {
            @ApiResponse(responseCode = "201", description = "Mensaje creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de mensaje inválidos")
        }
    )
    public ResponseEntity<Message> createMessage(@Validated @RequestBody Message message) {
        logger.debug("Solicitud para crear mensaje: {}", message);
        Message savedMessage = chatService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    /**
     * Endpoint para borrar un mensaje.
     * Si lo encuentra, lo borra y confirma. Si no, avisa que no existe.
     */
    @DeleteMapping("/messages/{id}")
    @Operation(
        summary = "Eliminar mensaje",
        description = "Borra un mensaje específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Mensaje eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
        }
    )
    public ResponseEntity<Map<String, Boolean>> deleteMessage(
            @Parameter(description = "ID del mensaje", required = true)
            @PathVariable Long id) {
        logger.debug("Solicitud para eliminar mensaje con ID: {}", id);
        return chatService.deleteMessage(id);
    }
}
