package com.gamecrew.gamecrew_project.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RecordOfRatings{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private Long user; // 평가 받은 사람

    @Column(nullable = false)
    private Long evaluator; //평가한 사람

    @Column(nullable = false)
    private int manner;

    @Column(nullable = false)
    private int participation;

    @Column(nullable = false)
    private int gamingSkill;

    @Column(nullable = false)
    private int enjoyable;

    @Column(nullable = false)
    private int sociability;

    @Column(nullable = false)
    private double totalRating;

    public RecordOfRatings(Long user, Long evaluator, int manner, int participation, int gamingSkill, int enjoyable, int sociability, double totalRating) {
        this.user = user;
        this.evaluator = evaluator;
        this.manner = manner;
        this.participation = participation;
        this.gamingSkill = gamingSkill;
        this.enjoyable = enjoyable;
        this.sociability = sociability;
        this.totalRating = Math.round(totalRating * 2) / 2.0;
    }
}

