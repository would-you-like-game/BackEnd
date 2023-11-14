package com.gamecrew.gamecrew_project.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String category;
    private String title;
    private String content;
    private Long totalNumber; // 전체 참가자 수

    private Long userId;
    private String nickname;
    private String userImg;
    private boolean isPostUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;



    public PostResponseDto(Post post, boolean isPostUser){
        this.postId = post.getPostId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.totalNumber = post.getTotalNumber();
        this.createdAt = post.getCreatedAt();

        this.userId = post.getUser().getUserId();
        this.nickname = post.getUser().getNickname();
        this.userImg = post.getUser().getUserImg();

        this.isPostUser = isPostUser;
    }
}
