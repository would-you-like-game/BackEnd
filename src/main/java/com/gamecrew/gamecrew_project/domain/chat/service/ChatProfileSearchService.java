package com.gamecrew.gamecrew_project.domain.chat.service;

import com.gamecrew.gamecrew_project.domain.user.dto.response.UserResponse;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatProfileSearchService {

    private final UserRepository userRepository;

    public List<UserResponse> searchProfileList(String nickname) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<User> userList = userRepository.findByNicknameContaining(nickname, pageable);

        return userList.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getNickname()
                ))
                .collect(Collectors.toList());
    }
}