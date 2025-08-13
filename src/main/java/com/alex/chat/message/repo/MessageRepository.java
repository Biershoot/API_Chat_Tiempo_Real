package com.alex.chat.message.repo;

import com.alex.chat.chat.entity.Chat;
import com.alex.chat.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatOrderBySentAtDesc(Chat chat, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId AND m.sentAt > :since ORDER BY m.sentAt ASC")
    List<Message> findNewMessagesByChatSince(Long chatId, LocalDateTime since);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat.id = :chatId AND :userId NOT MEMBER OF m.readBy")
    Long countUnreadMessages(Long chatId, Long userId);
}
