package com.gamecrew.gamecrew_project.domain.post.dto.response;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageNationResponseDto {
    private Long postId;
    private String title;
    private Long totalNumber;
    private Integer currentNumber;
    private String nickname;

    private int view;

    public PageNationResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.totalNumber = post.getTotalNumber();
        this.currentNumber = post.getCurrentNum();
        this.nickname = post.getUser().getNickname();
//        this.images = post.getImages();
//        this.temperature = post.getUser().getTemperature(); -> 온도 어떻게하지?
        this.view = post.getView();

    }

}
