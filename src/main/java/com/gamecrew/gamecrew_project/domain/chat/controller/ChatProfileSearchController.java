package com.gamecrew.gamecrew_project.domain.chat.controller;

import com.gamecrew.gamecrew_project.global.response.CustomResponse;
import com.gamecrew.gamecrew_project.domain.chat.service.ChatProfileSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatProfileSearchController {
    private final ChatProfileSearchService chatProfileSearchService;

    @RequestMapping("/api/search/profile")
    public CustomResponse<?> searchProfile(
            @RequestParam String nickname
    ){
        return CustomResponse.success("채팅 상대방들을 조회하셨습니다.",chatProfileSearchService.searchProfileList(nickname));
    }
}
