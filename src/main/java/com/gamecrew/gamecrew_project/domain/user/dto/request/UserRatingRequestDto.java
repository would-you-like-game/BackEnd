package com.gamecrew.gamecrew_project.domain.user.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class UserRatingRequestDto {
    @Min(0) @Max(10)
    private int manner;

    @Min(0) @Max(10)
    private int participation;

    @Min(0) @Max(10)
    private int gamingSkill;

    @Min(0) @Max(10)
    private int enjoyable;

    @Min(0) @Max(10)
    private int sociability;
}
