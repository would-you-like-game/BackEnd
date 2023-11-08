package com.gamecrew.gamecrew_project.domain.post.controller;

import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.service.PostService;
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

import java.util.Map;
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
    public MessageResponseDto createPost(@RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.createPost(requestDto, user);
        return new MessageResponseDto(Message.POST_SUCCESSFUL, HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = MessageResponseDto.class)))
    })
    @PutMapping("/{postId}")
    public MessageResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
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
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
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
    public PostResponseDto getPost(@PathVariable("postId") Long postId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(Objects.isNull(userDetails)){
            postService.updateView(postId);
            return postService.getPost(postId,null);
        }
        User user = userDetails.getUser();
        postService.updateView(postId);
        return postService.getPost(postId,user);
    }

    @Operation(summary = "게시글 페이지네이션", description = "게시글 페이지네이션")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @GetMapping("/category")
    //ResponseEntity를 안쓰려면 어떻게 할 수 있나? ResponseEntity의 반환값을 다른 방식으로 하려면?? PostResponseDto를 쓰려면?? 아니면 페이지네이션용 Dto를 따로 만들어야 하나??
    public ResponseEntity<Map<String, Object>> getCategoryPost(
            @RequestParam("category") String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Map<String, Object> pageNationResponseDtoList = postService.getCategoryPost(
                category,
                page - 1,
                size
        );
        return ResponseEntity.ok(pageNationResponseDtoList);
    }

//    @PostMapping("/{postId}")
//    public JoinPlayerResponseDto createJoinApply(@PathVariable("postId") Long postId,
//                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        User user = userDetails.getUser();
//        JoinPlayerResponseDto joinPlayerResponseDto;
//
//        try {
//            joinPlayerResponseDto = postService.createJoinApply(postId, user);
//        } catch (Exception e) {
//            return joinPlayerResponseDto = new JoinPlayerResponseDto(null, e.getMessage());
//        }
//
//        return joinPlayerResponseDto;
//    }



}
