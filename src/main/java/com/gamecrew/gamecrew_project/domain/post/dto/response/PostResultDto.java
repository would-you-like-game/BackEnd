package com.gamecrew.gamecrew_project.domain.post.dto.response;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResultDto {
    private Long postId;
    private String title;
    private Long totalNumber; // 전체 참가자 수
    private Long currentNumber; //참가자 수
    private String nickname;
    private String userImg;

    public PostResultDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.totalNumber = post.getTotalNumber();
        this.currentNumber = post.getCurrentNumber();
        this.nickname = post.getUser().getNickname();
        this.userImg = post.getUser().getUserImg();
    }
}
