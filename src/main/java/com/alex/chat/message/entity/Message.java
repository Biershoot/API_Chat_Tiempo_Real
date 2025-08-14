package com.alex.chat.message.entity;

import com.alex.chat.chat.entity.Chat;
import com.alex.chat.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * La clase que representa un mensaje en nuestra app.
 * Aquí guardo toda la información de cada mensaje que
 * alguien envía, incluyendo quién lo mandó y quién lo ha leído.
 */
@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * El ID único de cada mensaje.
     * Como el número de DNI pero para mensajes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A qué chat pertenece este mensaje.
     * Un mensaje siempre está dentro de una conversación.
     */
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    /**
     * Quién envió este mensaje.
     * Siempre hay que saber quién dijo qué.
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    /**
     * El texto del mensaje.
     * Lo que realmente dijo la persona.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * Cuándo se envió el mensaje.
     * Fecha y hora exactas para ordenar cronológicamente.
     */
    @Column(nullable = false)
    private LocalDateTime sentAt;

    /**
     * Qué usuarios han leído este mensaje.
     * Útil para los famosos "doble check" de WhatsApp.
     */
    @ManyToMany
    @JoinTable(
        name = "message_read_status",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> readBy = new HashSet<>();

    /**
     * Cuando se crea un mensaje nuevo, le pongo la hora actual.
     * Así no hay que acordarse de hacerlo manualmente cada vez.
     */
    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
}
