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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public MessageResponseDto createPost(@RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.createPost(requestDto, user);
        return new MessageResponseDto(Message.POST_SUCCESSFUL, HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.updatePost(postId, requestDto, user);
        return new MessageResponseDto(Message.POST_PUT_SUCCESSFUL, HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public MessageResponseDto deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.deletePost(postId, user);
        return new MessageResponseDto(Message.POST_DELETE_SUCCESSFUL, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable("postId") Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        postService.updateView(postId);
        return postService.getPost(postId,user);
    }

}
