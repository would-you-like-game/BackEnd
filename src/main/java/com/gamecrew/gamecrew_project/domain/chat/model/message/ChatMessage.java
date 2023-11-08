package com.gamecrew.gamecrew_project.domain.chat.model.message;

public record ChatMessage(
        String roomKey,
        String nickname,
        String msg
) {
}