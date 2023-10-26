package com.gamecrew.gamecrew_project.domain.post.repository;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);
}
