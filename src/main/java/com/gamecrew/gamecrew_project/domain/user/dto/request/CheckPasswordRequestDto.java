package com.gamecrew.gamecrew_project.domain.user.dto.request;

import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CheckPasswordRequestDto {
    @NotBlank(message = ErrorMessage.PASSWORD_NOT_BLANK)
    @Size(min = 8, max = 20, message = ErrorMessage.INVALID_PASSWORD_LENGTH)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^<>&*]).*$", message = ErrorMessage.INVALID_PASSWORD_FORMAT)
    private String password;
}
