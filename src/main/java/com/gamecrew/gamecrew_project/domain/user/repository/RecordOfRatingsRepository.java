package com.gamecrew.gamecrew_project.domain.user.repository;

import com.gamecrew.gamecrew_project.domain.user.entity.RecordOfRatings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordOfRatingsRepository extends JpaRepository<RecordOfRatings, Long> {

    Long countByUserId(Long userId);

    List<RecordOfRatings> findTop9ByUserIdOrderByRecordedAtDesc(Long userId, PageRequest pageable);
}