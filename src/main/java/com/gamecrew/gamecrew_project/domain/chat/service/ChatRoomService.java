package com.gamecrew.gamecrew_project.domain.chat.service;

import com.gamecrew.gamecrew_project.domain.chat.model.entity.ChatRoom;
import com.gamecrew.gamecrew_project.domain.chat.repository.ChatRoomRepository;
import com.gamecrew.gamecrew_project.domain.chat.model.response.ChatRoomResponse;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.ChatExceptionImpl;
import com.gamecrew.gamecrew_project.global.exception.type.ChatErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public void createChatRoom(User user, Long receiver) {
        userRepository.findById(receiver)
                .orElseThrow(() -> {
                    throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                });
        UUID uuid = UUID.randomUUID();
        chatRoomRepository.save(ChatRoom.builder()
                .roomKey(uuid.toString())
                .sender(user.getId())
                .receiver(receiver)
                .build());
    }

    public List<ChatRoomResponse> searchProfileList(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> {
                    throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                });

        List<ChatRoomResponse> tweetUserResponses = new ArrayList<>();

        List<ChatRoom> chatRoomList = chatRoomRepository.findAllBySenderOrReceiver(user.getId(), user.getId());

        for (ChatRoom chatRoom : chatRoomList) {
            User otherUser;
            if (chatRoom.getSender().equals(user.getId())) {
                otherUser = userRepository.findById(chatRoom.getReceiver())
                        .orElseThrow(() -> new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER));
            } else {
                otherUser = userRepository.findById(chatRoom.getSender())
                        .orElseThrow(() -> new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER));
            }
            tweetUserResponses.add(new ChatRoomResponse(otherUser.getId(), otherUser.getNickname(), chatRoom.getRoomKey()));
        }

        return tweetUserResponses;
    }

}
