package com.gamecrew.gamecrew_project.domain.user.dto.request;

import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank(message = ErrorMessage.NICKNAME_NOT_BLANK)
    @Size(max = 9, message = ErrorMessage.INVALID_NICKNAME_LENGTH)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = ErrorMessage.INVALID_NICKNAME_FORMAT)
    private String nickname;
}
