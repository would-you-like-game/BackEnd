package com.gamecrew.gamecrew_project.domain.user.dto.request;

import com.gamecrew.gamecrew_project.global.message.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank(message = ErrorMessage.EMAIL_NOT_BLANK)
    @Size(min = 12, max = 30, message = ErrorMessage.INVALID_EMAIL_LENGTH)
    @Email(message = ErrorMessage.INVALID_EMAIL_FORMAT)
    private String email;

    @NotBlank(message = ErrorMessage.NICKNAME_NOT_BLANK)
    @Size(max = 9, message = ErrorMessage.INVALID_NICKNAME_LENGTH)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = ErrorMessage.INVALID_NICKNAME_FORMAT)
    private String nickname;

    @NotBlank(message = ErrorMessage.PASSWORD_NOT_BLANK)
    @Size(min = 8, max = 20, message = ErrorMessage.INVALID_PASSWORD_LENGTH)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^<>&*]).*$", message = ErrorMessage.INVALID_PASSWORD_FORMAT)
    private String password;
}
