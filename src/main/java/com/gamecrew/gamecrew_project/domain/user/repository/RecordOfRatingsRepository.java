package com.gamecrew.gamecrew_project.domain.user.repository;

import com.gamecrew.gamecrew_project.domain.user.entity.RecordOfRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordOfRatingsRepository extends JpaRepository<RecordOfRatings, Long> {

    Long countByUser(Long userId);
}