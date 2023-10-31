package com.gamecrew.gamecrew_project.domain.user.repository;

import com.gamecrew.gamecrew_project.domain.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByEmailAndCode(String email, String code);
}
