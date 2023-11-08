package com.gamecrew.gamecrew_project.domain.chat.repository;

import com.gamecrew.gamecrew_project.domain.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
