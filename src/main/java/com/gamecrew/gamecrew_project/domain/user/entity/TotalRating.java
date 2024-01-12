package com.gamecrew.gamecrew_project.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TotalRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private Long userId; // 평가 받은 사람

    @Column(nullable = false)
    private double totalManner;

    @Column(nullable = false)
    private double totalParticipation;

    @Column(nullable = false)
    private double totalGamingSkill;

    @Column(nullable = false)
    private double totalEnjoyable;

    @Column(nullable = false)
    private double totalSociability;

    @Column(nullable = false)
    private double totalRating;

    public TotalRating(Long evaluated_user, double totalManner, double totalParticipation, double totalGamingSkill,
                       double totalEnjoyable, double totalSociability, double totalRating) {
        this.userId = evaluated_user;
        this.totalManner = totalManner;
        this.totalParticipation = totalParticipation;
        this.totalGamingSkill = totalGamingSkill;
        this.totalEnjoyable = totalEnjoyable;
        this.totalSociability = totalSociability;
        this.totalRating = Math.round(totalRating * 2) / 2.0;;
    }

    public TotalRating(Long userId) {
        this.userId = userId;
    }

    public void setTotalManner(double totalManner) {
        this.totalManner = Math.round(totalManner * 2) / 2.0;;
    }

    public void setTotalParticipation(double totalParticipation) {
        this.totalParticipation = Math.round(totalParticipation * 2) / 2.0;;
    }

    public void setTotalGamingSkill(double totalGamingSkill) {
        this.totalGamingSkill = Math.round(totalGamingSkill * 2) / 2.0;;
    }

    public void setTotalEnjoyable(double totalEnjoyable) {
        this.totalEnjoyable = Math.round(totalEnjoyable * 2) / 2.0;;
    }

    public void setTotalSociability(double totalSociability) {
        this.totalSociability = Math.round(totalSociability * 2) / 2.0;;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = Math.round(totalRating * 2) / 2.0;;
    }
}