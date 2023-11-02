package com.gamecrew.gamecrew_project.domain.post.repository;

import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);

    Page<Post> findAllByCategory(String category, Pageable pageable);

    List<Post> findAllByUserEmail(String userEmail);

}
