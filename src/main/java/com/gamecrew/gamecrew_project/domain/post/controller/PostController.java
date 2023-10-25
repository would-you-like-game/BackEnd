package com.gamecrew.gamecrew_project.domain.post.controller;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.service.PostService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public MessageResponseDto createPost(@RequestBody PostRequestDto requestDto){
        postService.createPost(requestDto);
        return new MessageResponseDto(Message.POST_SUCCESSFUL, HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto){
        postService.updatePost(postId, requestDto);
        return new MessageResponseDto(Message.POST_PUT_SUCCESSFUL, HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public MessageResponseDto deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new MessageResponseDto(Message.POST_DELETE_SUCCESSFUL, HttpStatus.OK);
    }


}
