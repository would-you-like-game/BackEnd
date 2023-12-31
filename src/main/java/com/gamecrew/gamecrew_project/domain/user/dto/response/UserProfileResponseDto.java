package com.gamecrew.gamecrew_project.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDto {
    private String userImg;
    private String nickname;
    private String email;
    private UserTotalRatingResponseDto result;
    private Long numberOfEvaluations;

    public UserProfileResponseDto(
            String userImg,
            String nickname,
            String email,
            UserTotalRatingResponseDto userTotalRatingResponseDto,
            Long numberOfEvaluations
    ){
        this.userImg = userImg;
        this.nickname = nickname;
        this.email = email;
        this.result = userTotalRatingResponseDto;
        this.numberOfEvaluations = numberOfEvaluations;
    }
}
