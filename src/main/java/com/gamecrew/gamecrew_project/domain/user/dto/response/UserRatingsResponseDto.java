package com.gamecrew.gamecrew_project.domain.user.dto.response;

import com.gamecrew.gamecrew_project.global.response.CustomPageable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRatingsResponseDto {
    private String msg;
    private CustomPageable pageable;
    private List<UserRatingResultDto> result;

    public UserRatingsResponseDto(
            String msg,
            int totalPages,
            long totalElements,
            int size,
            List<UserRatingResultDto> userRatingResultDto
    ) {
        this.msg = msg;
        this.pageable = new CustomPageable(totalPages, totalElements, size);
        this.result = userRatingResultDto;
    }

    @Getter
    public class UserRatingResultDto {
        private Long evaluator; //평가를 한 사람
        private int manner;
        private int participation;
        private int gamingSkill;
        private int enjoyable;
        private int sociability;

        public UserRatingResultDto(Long evaluator,
                                   int manner,
                                   int participation,
                                   int gamingSkill,
                                   int enjoyable,
                                   int sociability){
            this.evaluator = evaluator;
            this.manner = manner;
            this.participation = participation;
            this.gamingSkill = gamingSkill;
            this.enjoyable = enjoyable;
            this.sociability = sociability;

        }
    }
}
