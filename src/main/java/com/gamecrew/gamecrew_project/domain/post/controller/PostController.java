package com.gamecrew.gamecrew_project.domain.post.controller;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostsResponseDto;
import com.gamecrew.gamecrew_project.domain.post.service.PostService;
import com.gamecrew.gamecrew_project.domain.post.type.CategoryType;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 작성", description = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class)))
    })
    @PostMapping("")
    public MessageResponseDto createPost(@RequestPart("post") PostRequestDto requestDto,
                                         @RequestPart("photos") List<MultipartFile> photos,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        User user = userDetails.getUser();
        postService.createPost(requestDto, user, photos);
        return new MessageResponseDto(Message.POST_SUCCESSFUL, HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class)))
    })
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        postService.updatePost(postId, requestDto, user);
        return new MessageResponseDto(Message.POST_PUT_SUCCESSFUL, HttpStatus.OK);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class)))
    })
    @DeleteMapping("/{postId}")
    public MessageResponseDto deletePost(@PathVariable Long postId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        postService.deletePost(postId, user);
        return new MessageResponseDto(Message.POST_DELETE_SUCCESSFUL, HttpStatus.OK);
    }

    @Operation(summary = "게시글 상세조회", description = "게시글 상세조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = PostResponseDto.class)))
    })
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(Objects.isNull(userDetails)){
            return postService.getPost(postId,null);
        }
        User user = userDetails.getUser();
        return postService.getPost(postId,user);
    }

    @Operation(summary = "게시글 전체조회", description = "게시글 전체조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @GetMapping("")
    public PostsResponseDto getCategoryPost(
            @RequestParam CategoryType category,
            @RequestParam int page,
            @RequestParam int size) {
        return postService.getCategoryPost(category, page - 1, size);
    }
}
