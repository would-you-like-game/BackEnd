package com.gamecrew.gamecrew_project.domain.user.entity;

import com.gamecrew.gamecrew_project.global.entity.Auditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String userImg;

    public User(String email, String nickname, String password, String userImg) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userImg = userImg;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserImg(String imageUrl) {
        this.userImg = imageUrl;
    }
}
