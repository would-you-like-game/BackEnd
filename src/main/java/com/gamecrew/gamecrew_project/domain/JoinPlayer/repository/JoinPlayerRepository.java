package com.gamecrew.gamecrew_project.domain.JoinPlayer.repository;

import com.gamecrew.gamecrew_project.domain.JoinPlayer.entity.JoinPlayer;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinPlayerRepository extends JpaRepository<JoinPlayer,Long> {

    List<JoinPlayer> findByPost(Post post);
    List<JoinPlayer> findJoinPlayersByPostIn(List<Post> posts);
}
