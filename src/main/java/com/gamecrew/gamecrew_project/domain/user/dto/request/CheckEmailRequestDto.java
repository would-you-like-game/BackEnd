package com.gamecrew.gamecrew_project.domain.user.dto.request;

import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CheckEmailRequestDto {
    @NotBlank(message = ErrorMessage.EMAIL_NOT_BLANK)
    @Size(min = 12, max = 30, message = ErrorMessage.INVALID_EMAIL_LENGTH)
    @Email(message = ErrorMessage.INVALID_EMAIL_FORMAT)
    private String email;
}
