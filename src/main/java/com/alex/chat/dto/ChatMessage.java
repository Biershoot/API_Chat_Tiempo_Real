package com.alex.chat.dto;

/**
 * La estructura simple de un mensaje para el WebSocket.
 * Es como un sobre que usamos para enviar mensajes entre clientes.
 * Más ligero que la entidad Message completa.
 */
public class ChatMessage {
    private String sender;
    private String content;
    private String timestamp;

    /**
     * Constructor vacío porque Spring lo necesita.
     * Como cuando preparas un sobre antes de escribir nada.
     */
    public ChatMessage() {}

    /**
     * Constructor con todos los datos del mensaje.
     * Para cuando ya sabes todo lo que quieres mandar.
     */
    public ChatMessage(String sender, String content, String timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters y setters - las puertas de entrada y salida de los datos

    /**
     * Dice quién envió el mensaje.
     */
    public String getSender() { return sender; }

    /**
     * Cambia quién envió el mensaje.
     */
    public void setSender(String sender) { this.sender = sender; }

    /**
     * Obtiene el texto del mensaje.
     */
    public String getContent() { return content; }

    /**
     * Establece el texto del mensaje.
     */
    public void setContent(String content) { this.content = content; }

    /**
     * Devuelve la hora en que se envió.
     */
    public String getTimestamp() { return timestamp; }

    /**
     * Actualiza la hora del mensaje.
     */
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
