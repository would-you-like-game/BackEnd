package com.gamecrew.gamecrew_project.domain.post.service;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResultDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostsResponseDto;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.post.entity.PostImg;
import com.gamecrew.gamecrew_project.domain.post.repository.PostImgRepository;
import com.gamecrew.gamecrew_project.domain.post.repository.PostRepository;
import com.gamecrew.gamecrew_project.domain.post.type.CategoryType;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostS3Service postS3Service;
    private final PostImgRepository postImgRepository;


    public void createPost(PostRequestDto requestDto, User user, List<MultipartFile> photos) throws IOException {
        List<PostImg> postImgList = null;
        if (photos != null && !photos.isEmpty()) {
            postImgList = postS3Service.uploadPhotosToS3AndCreatePostImages(photos);
        }

        Post post = new Post(requestDto, user);
        postRepository.save(post);

        if (postImgList != null) {
            associatePostImagesWithPost(post, postImgList);
        }
    }

    //PostImg 객체와 Post를 연결하고 DB에 저장
    private void associatePostImagesWithPost(Post post, List<PostImg> postImgList) {
        for (PostImg postImg : postImgList) {
            postImg.setPost(post);
            postImgRepository.save(postImg);
        }
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }
        // DB수정
        post.update(requestDto);
    }

    public void deletePost(Long postId, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public PostResponseDto getPost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NON_EXISTENT_POST, HttpStatus.BAD_REQUEST));

        post.increaseViewCount();
        postRepository.save(post);

        boolean isPostUser = false;
        if (user != null) {
            // Post의 소유자가 현재 User인지 확인합니다.
            isPostUser = user.getUserId().equals(post.getUser().getUserId());
        }
        return new PostResponseDto(post, isPostUser);
    }


    public PostsResponseDto getCategoryPost(CategoryType category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        if (category.equals(CategoryType.all)) {
            Page<Post> postList = postRepository.findAll(pageable);

            // Post 객체를 PostResultDto 객체로 변환
            List<PostResultDto> PostResultDtos = postList.getContent().stream()
                    .map(PostResultDto::new)
                    .collect(Collectors.toList());

            return new PostsResponseDto(Message.GET_POST_SUCCESSFUL, postList.getTotalPages(), postList.getTotalElements(), size, PostResultDtos);

        } else {
            Page<Post> postListCategory = postRepository.findAllByCategory(category.name(), pageable);

            List<PostResultDto> PostResultDtos = postListCategory.getContent().stream()
                    .map(PostResultDto::new)
                    .collect(Collectors.toList());

            return new PostsResponseDto(Message.GET_POST_SUCCESSFUL, postListCategory.getTotalPages(), postListCategory.getTotalElements(), size, PostResultDtos);
        }
    }
}

