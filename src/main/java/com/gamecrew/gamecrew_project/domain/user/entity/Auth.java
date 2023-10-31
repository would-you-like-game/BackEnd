package com.gamecrew.gamecrew_project.domain.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auth {
    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    public Auth(String email, String authCode) {
        this.email = email;
        this.code = authCode;
    }
}

