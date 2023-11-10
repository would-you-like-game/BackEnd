package com.gamecrew.gamecrew_project.domain.chat.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamecrew.gamecrew_project.global.entity.Auditing;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatRoomsResponseDto {
    private String msg;
    private ChatRoomPageable pageable;
    private List<ChatRoomsResultDto> result;

    public ChatRoomsResponseDto(
            String msg,
            int totalPages,
            long totalElements,
            int size,
            List<ChatRoomsResultDto> chatRooms
    ){
        this.msg = msg;
        this.pageable = new ChatRoomPageable(totalPages, totalElements, size);
        this.result = chatRooms;
    }

    @Getter
    public static class ChatRoomsResultDto {
            private String roomKey;
            private String nickname; //상대방 닉네임
            private String userImg; //상대방 프로필 사진
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime modifiedAt;
        public ChatRoomsResultDto(String roomKey, String nickname, String userImg, LocalDateTime modifiedAt){
            this.roomKey = roomKey;
            this.nickname = nickname;
            this.userImg = userImg;
            this.modifiedAt = modifiedAt;
        }
    }

    @Getter
    private class ChatRoomPageable {
        private int totalPages;
        private long totalElements;
        private int size;

        public ChatRoomPageable(int totalPages, long totalElements, int size) {
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.size = size;
        }
    }

}
