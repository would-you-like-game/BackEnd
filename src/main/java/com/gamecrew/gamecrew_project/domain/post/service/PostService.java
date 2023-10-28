package com.gamecrew.gamecrew_project.domain.post.service;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.post.repository.PostRepository;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void createPost(PostRequestDto requestDto, User user) {
        // RequestDto -> Entity
        Post post = new Post(requestDto, user);
        //DB저장
        Post savePost = postRepository.save(post);
    }
    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))){
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");}
        // DB수정
        post.update(requestDto);
    }
    public void deletePost(Long postId, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))){
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");}

        postRepository.delete(post);
    }
    @Transactional
    public void updateView(Long postId) {
        Post post = postRepository.findByPostId(postId);
        post.update();
    }

    public PostResponseDto getPost(Long postId, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        PostResponseDto postResponseDto;
        if(post.getUser().getEmail().equals(user.getEmail())){
            postResponseDto = new PostResponseDto(post);
            postResponseDto.checkOwner();
            return postResponseDto;
        } else {
            postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        }
    }

    public Map<String, Object> getAllPost(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy,"title");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postList = postRepository.findAll(pageable);

        List<PostResponseDto> postResponseDtoList = postList.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("postList", postResponseDtoList);
        response.put("totalPages", postList.getTotalPages());

        return response;
    }
}
