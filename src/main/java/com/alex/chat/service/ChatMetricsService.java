package com.alex.chat.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio para gestionar métricas de chat en tiempo real.
 * Registra contadores, medidores y temporizadores para monitorear el rendimiento
 * y la actividad del sistema de chat.
 */
@Service
public class ChatMetricsService {
    private static final Logger logger = LoggerFactory.getLogger(ChatMetricsService.class);

    private final Counter messagesCounter;
    private final Counter userConnectionCounter;
    private final Counter userDisconnectionCounter;
    private final AtomicInteger connectedUsers;
    private final Timer messageProcessingTimer;
    private final ConcurrentHashMap<String, AtomicInteger> activeUsersPerRoom;

    /**
     * Constructor que inicializa los medidores y contadores.
     */
    public ChatMetricsService(MeterRegistry meterRegistry) {
        logger.info("Inicializando servicio de métricas para el chat");

        // Contadores
        this.messagesCounter = Counter.builder("chat.messages.sent")
                .description("Número total de mensajes enviados")
                .register(meterRegistry);

        this.userConnectionCounter = Counter.builder("chat.users.connections")
                .description("Número total de conexiones de usuarios")
                .register(meterRegistry);

        this.userDisconnectionCounter = Counter.builder("chat.users.disconnections")
                .description("Número total de desconexiones de usuarios")
                .register(meterRegistry);

        // Medidor de usuarios conectados
        this.connectedUsers = new AtomicInteger(0);
        Gauge.builder("chat.users.active", connectedUsers, AtomicInteger::get)
                .description("Número de usuarios actualmente conectados")
                .register(meterRegistry);

        // Temporizador para medir latencia de procesamiento de mensajes
        this.messageProcessingTimer = Timer.builder("chat.messages.processing.time")
                .description("Tiempo que toma procesar un mensaje")
                .register(meterRegistry);

        // Mapa para seguir usuarios por sala
        this.activeUsersPerRoom = new ConcurrentHashMap<>();
    }

    /**
     * Incrementa el contador de mensajes enviados.
     */
    public void incrementMessageCount() {
        messagesCounter.increment();
        logger.debug("Contador de mensajes incrementado");
    }

    /**
     * Registra una nueva conexión de usuario.
     */
    public void recordUserConnection() {
        userConnectionCounter.increment();
        connectedUsers.incrementAndGet();
        logger.debug("Usuario conectado. Total: {}", connectedUsers.get());
    }

    /**
     * Registra una desconexión de usuario.
     */
    public void recordUserDisconnection() {
        userDisconnectionCounter.increment();
        connectedUsers.decrementAndGet();
        logger.debug("Usuario desconectado. Total: {}", connectedUsers.get());
    }

    /**
     * Registra usuarios activos en una sala específica.
     */
    public void recordUserJoinedRoom(String roomId) {
        activeUsersPerRoom.computeIfAbsent(roomId, k -> new AtomicInteger(0)).incrementAndGet();
        logger.debug("Usuario unido a sala: {}. Total en sala: {}", roomId,
                activeUsersPerRoom.get(roomId).get());
    }

    /**
     * Registra cuando un usuario sale de una sala.
     */
    public void recordUserLeftRoom(String roomId) {
        if (activeUsersPerRoom.containsKey(roomId)) {
            activeUsersPerRoom.get(roomId).decrementAndGet();
            logger.debug("Usuario salió de sala: {}. Total en sala: {}", roomId,
                    activeUsersPerRoom.get(roomId).get());
        }
    }

    /**
     * Mide el tiempo de procesamiento de un mensaje.
     *
     * @param result Código a ejecutar y medir
     * @return El resultado del código ejecutado
     */
    public <T> T timeMessageProcessing(Timer.Sample sample, T result) {
        sample.stop(messageProcessingTimer);
        return result;
    }

    /**
     * Crea una muestra de tiempo para medir duración de procesamiento.
     */
    public Timer.Sample startMessageProcessingTimer() {
        return Timer.start();
    }
}
