package com.gamecrew.gamecrew_project.domain.user.dto.response;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTotalRatingResponseDto {
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalManner;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalParticipation;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalGamingSkill;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalEnjoyable;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalSociability;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double totalRating;
    public UserTotalRatingResponseDto(double totalManner,
                                      double totalParticipation,
                                      double totalGamingSkill,
                                      double totalEnjoyable,
                                      double totalSociability,
                                      double totalRating){
        this.totalManner = totalManner;
        this.totalParticipation = totalParticipation;
        this.totalGamingSkill = totalGamingSkill;
        this.totalEnjoyable = totalEnjoyable;
        this.totalSociability = totalSociability;
        this.totalRating = totalRating;
    }
}
