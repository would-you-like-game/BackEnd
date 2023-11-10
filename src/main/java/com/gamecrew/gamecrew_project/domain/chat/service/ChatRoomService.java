package com.gamecrew.gamecrew_project.domain.chat.service;

import com.gamecrew.gamecrew_project.domain.chat.model.entity.ChatRoom;
import com.gamecrew.gamecrew_project.domain.chat.model.response.ChatRoomsResponseDto;
import com.gamecrew.gamecrew_project.domain.chat.repository.ChatRoomRepository;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public MessageResponseDto createChatRoom(Long senderId, Long receiverId) {
        String roomId = generateRoomId(senderId, receiverId);

        Optional<ChatRoom> existingRoom = chatRoomRepository.findByRoomKey(roomId);
        if (existingRoom.isPresent()) {
            throw new CustomException(ErrorMessage.DUPLICATE_CHATROOM_EXISTS, HttpStatus.CONFLICT, false);
        }

        ChatRoom newRoom = ChatRoom.builder()
                .roomKey(roomId)
                .sender(senderId)
                .receiver(receiverId)
                .build();

        chatRoomRepository.save(newRoom);

        return new MessageResponseDto(Message.CREATE_CHAT_ROOM + " : " + roomId, HttpStatus.OK);
    }

    private String generateRoomId(Long senderId, Long receiverId) {
        if (senderId.compareTo(receiverId) > 0) {
            return receiverId + "-" + senderId;
        } else {
            return senderId + "-" + receiverId;
        }
    }

    public ChatRoomsResponseDto getChatRooms(Long userId, int page, int size) {
        String userIdStr = String.valueOf(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<ChatRoom> pageResult = chatRoomRepository.findByRoomKeyContaining(userIdStr, pageable);


        List<ChatRoomsResponseDto.ChatRoomsResultDto> result = pageResult.getContent().stream()
                .map(chatRoom -> {
                    Long otherUserId = chatRoom.getSender().equals(userId) ? chatRoom.getReceiver() : chatRoom.getSender();
                    Optional<User> otherUserOpt = userRepository.findByUserId(otherUserId);

                    if (!otherUserOpt.isPresent()) {
                        throw new IllegalArgumentException("상대방 사용자를 찾을 수 없습니다.");
                    }

                    User otherUser = otherUserOpt.get();
                    return new ChatRoomsResponseDto.ChatRoomsResultDto(chatRoom.getRoomKey(), otherUser.getNickname(),otherUser.getUserImg(), chatRoom.getModifiedAt());
                })
                .collect(Collectors.toList());

        return new ChatRoomsResponseDto(
                Message.SEARCH_CHAT_ROOM,
                pageResult.getTotalPages(),
                pageResult.getTotalElements(),
                pageResult.getSize(),
                result
        );
    }
}