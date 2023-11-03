package com.gamecrew.gamecrew_project.domain.post.dto.response;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long postId;
    private String title;
    private String category;
    private String content;
    private Long totalNumber;
    private LocalDateTime createdAt;
    private String nickname;
//    private List<String> images;
    private Long id;
//    private Temperature temperature; -> 온도 어떻게하지?
    private int view;

    private boolean isOwner =false;
//    private Boolean isASC;

    public PostResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.totalNumber = post.getTotalNumber();
        this.createdAt = post.getCreatedAt();
        this.nickname = post.getUser().getNickname();
//        this.images = post.getImages();
        this.id = post.getUser().getUserId();
//        this.temperature = post.getUser().getTemperature(); -> 온도 어떻게하지?
        this.view = post.getView();
//        this.isASC = post.getIsASC();
    }
    public void checkOwner(){
        isOwner = true;
    }

}
