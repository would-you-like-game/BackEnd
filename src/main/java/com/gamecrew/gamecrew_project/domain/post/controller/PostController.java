package com.gamecrew.gamecrew_project.domain.post.controller;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.service.PostService;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //게시글 작성 api
    @PostMapping("")
    public MessageResponseDto createPost(@RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.createPost(requestDto, user);
        return new MessageResponseDto(Message.POST_SUCCESSFUL, HttpStatus.OK);
    }
    //게시글 수정 api
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.updatePost(postId, requestDto, user);
        return new MessageResponseDto(Message.POST_PUT_SUCCESSFUL, HttpStatus.OK);
    }
    //게시글 삭제 api
    @DeleteMapping("/{postId}")
    public MessageResponseDto deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.deletePost(postId, user);
        return new MessageResponseDto(Message.POST_DELETE_SUCCESSFUL, HttpStatus.OK);
    }
    //게시글 상세조회 api
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable("postId") Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        postService.updateView(postId);
        return postService.getPost(postId,user);
    }
    //페이지네이션 api
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllPost(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sortBy") String sortBy,
                                                          @RequestParam("isAsc") boolean isAsc) {
        Map<String, Object> postResponseDtoList = postService.getAllPost(
                page -1,
                size,
                sortBy,
                isAsc
        );
        return ResponseEntity.ok(postResponseDtoList);
    }

}
