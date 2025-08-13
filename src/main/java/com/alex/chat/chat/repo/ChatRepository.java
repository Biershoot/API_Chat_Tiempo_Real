package com.alex.chat.chat.repo;

import com.alex.chat.chat.entity.Chat;
import com.alex.chat.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.id = :userId")
    List<Chat> findAllByParticipantId(Long userId);

    @Query("SELECT c FROM Chat c WHERE c.isGroupChat = false AND :user1 MEMBER OF c.participants AND :user2 MEMBER OF c.participants")
    Chat findPrivateChatBetweenUsers(User user1, User user2);
}
