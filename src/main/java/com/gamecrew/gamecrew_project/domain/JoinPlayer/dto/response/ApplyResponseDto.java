package com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyResponseDto {
    private Long postId;

    private String msg;

    public ApplyResponseDto(Long postId, String msg) {
        this.postId = postId;
        this.msg = msg;
    }
}
