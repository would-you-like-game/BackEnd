package com.gamecrew.gamecrew_project.domain.user.dto.request;

import lombok.Getter;

@Getter
public class EmailCodeRequestDto {
    private String code;
    private String email;
}
