package com.gamecrew.gamecrew_project.domain.user.repository;

import com.gamecrew.gamecrew_project.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    Page<User> findByNicknameContaining(String search, Pageable pageable);
}
