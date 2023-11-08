package com.gamecrew.gamecrew_project.domain.JoinPlayer.controller;

import com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response.ApplyResponseDto;
import com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response.JoinPlayerResponseDto;
import com.gamecrew.gamecrew_project.domain.JoinPlayer.service.JoinPlayerService;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
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

import java.util.List;

@RestController
@RequestMapping("/post/joinPlayer")
@RequiredArgsConstructor
public class JoinPlayerController {

    private final JoinPlayerService joinPlayerService;

    @Operation(summary = "크루 가입신청", description = "크루 가입신청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @PostMapping("/{postId}")
    public ApplyResponseDto createJoinApply(@PathVariable("postId") Long postId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        ApplyResponseDto applyResponseDto;

        try {
            applyResponseDto = joinPlayerService.createJoinApply(postId, user);
        } catch (Exception e) {
            return applyResponseDto = new ApplyResponseDto(null, e.getMessage());
        }

        return applyResponseDto;
    }


    @Operation(summary = "크루 신청자 승인", description = "크루 신청자 승인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity acceptJoinApply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long postId ) {
        String userEmail = userDetails.getUser().getEmail();

        try {
            joinPlayerService.acceptJoinApply(userEmail, postId);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "크루신청자 조회", description = "크루신청자 조회 api 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<List<JoinPlayerResponseDto>> getJoinPlayer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                     @PathVariable Long postId){
        String userEmail = userDetails.getUser().getEmail();

        return ResponseEntity.ok(joinPlayerService.getJoinPlayerList(userEmail, postId));
    }

}
