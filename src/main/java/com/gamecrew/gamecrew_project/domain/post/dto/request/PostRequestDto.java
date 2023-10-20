package com.gamecrew.gamecrew_project.domain.post.dto.request;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRequestDto {

    @NotBlank(message = ErrorMessage.TITLE_NOT_BLANK)
    @Size(max = 20, message = ErrorMessage.INVALID_TITLE_LENGTH)
    private String title;

    @NotBlank(message = ErrorMessage.CONTENT_NOT_BLANK)
    @Size(max = 300, message = ErrorMessage.INVALID_CONTENT_LENGTH)
    private String content;

    @NotBlank(message = ErrorMessage.TOTALNUMBER_NOT_BLANK)
    @Size(max = 255, message = ErrorMessage.INVALID_NICKNAME_LENGTH)
    private Long totalNumber;

    private String category;

}