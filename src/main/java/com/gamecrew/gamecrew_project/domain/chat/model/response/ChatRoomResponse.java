package com.gamecrew.gamecrew_project.domain.chat.model.response;

public record ChatRoomResponse(
        Long id,
        String nickname,
        String roomKey
) {
}
