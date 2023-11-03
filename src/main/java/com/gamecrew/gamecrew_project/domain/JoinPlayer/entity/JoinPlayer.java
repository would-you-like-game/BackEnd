package com.gamecrew.gamecrew_project.domain.JoinPlayer.entity;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JoinPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinPlayerId;

    private String nickname;

    private String userEMail;

    private Boolean isAccepted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public JoinPlayer(Post post,User user, String nickname, String userEmail) {
        this.user = user;
        this.nickname = nickname;
        this.userEMail = userEmail;
        this.post = post;
    }

    public void updateAccepted(){
        isAccepted = true;
    }

}
