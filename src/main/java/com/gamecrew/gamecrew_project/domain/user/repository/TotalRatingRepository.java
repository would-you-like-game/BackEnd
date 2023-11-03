package com.gamecrew.gamecrew_project.domain.user.repository;

import com.gamecrew.gamecrew_project.domain.user.entity.TotalRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalRatingRepository extends JpaRepository<TotalRating, Long> {

    Optional<TotalRating> findByEvaluatedUserId(Long userId);
}
