package com.gamecrew.gamecrew_project.domain.post.service;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.post.repository.PostRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);
        //DB저장
        Post savePost = postRepository.save(post);
    }
    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        // DB수정
        post.update(requestDto);
    }
    public void deletePost(Long postId) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        postRepository.delete(post);
    }
}
