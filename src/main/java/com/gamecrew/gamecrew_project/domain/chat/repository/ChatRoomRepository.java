package com.gamecrew.gamecrew_project.domain.chat.repository;

import com.gamecrew.gamecrew_project.domain.chat.model.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomKey(String roomId);
    Page<ChatRoom> findByRoomKeyContaining(String userId, Pageable pageable);

}
