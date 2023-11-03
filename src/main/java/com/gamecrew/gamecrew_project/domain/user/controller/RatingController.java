package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.dto.request.UserRatingRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserTotalRatingResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.domain.user.service.RatingService;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class RatingController {

    private final RatingService ratingService;
    private final UserRepository userRepository;

    //유저의 평점을 DB에 등록하는 API
    @PostMapping("/rating/{evaluated_user}")
    public MessageResponseDto registrationOfRatings(@RequestBody UserRatingRequestDto userRatingRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long evaluated_user){
        if (userRepository.findById(evaluated_user).isEmpty()) throw new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST, false);

        User evaluator = userDetails.getUser();
        ratingService.registrationOfRatings(userRatingRequestDto, evaluator, evaluated_user);

        return new MessageResponseDto(Message.REGISTRATION_COMPLETED, HttpStatus.OK);
    }

    //유저의 평점을 가져오는 API
    @GetMapping("/getRating/{evaluated_user}")
    public UserTotalRatingResponseDto getUserRating(@PathVariable Long evaluated_user){
        return ratingService.getUserRating(evaluated_user);
    }
}
