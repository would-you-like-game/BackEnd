package com.gamecrew.gamecrew_project.domain.post.entity;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.global.entity.Auditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "totalNumber", nullable = false)
    private Long totalNumber;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "currentNum", nullable = false)
    private Long currentNumber = 1L;

    private Long postViewCount;

    public Post(PostRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.totalNumber = requestDto.getTotalNumber();
        this.category = requestDto.getCategory();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.totalNumber = requestDto.getTotalNumber();
        this.category = requestDto.getCategory();
    }

    public void recruitCount() {
        currentNumber++;
    }

    public boolean isFullJoin() {
        if (totalNumber > currentNumber) {
            return false;
        } else {
            return true;
        }
    }

//    public void increaseViewCount() {
//        this.postViewCount += 1;
//    }
public void increaseViewCount() {
    // postViewCount가 null이면 0으로 초기화
    if (this.postViewCount == null) {
        this.postViewCount = 0L; // 혹은 다른 초기값으로 설정
    }

    // 기존 로직 계속 수행
    this.postViewCount += 1;
    }
}
