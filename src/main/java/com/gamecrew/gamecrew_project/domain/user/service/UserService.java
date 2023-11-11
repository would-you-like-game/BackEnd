package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckNicknameRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckPasswordRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserProfileResponseDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserTotalRatingResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.TotalRating;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.RecordOfRatingsRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.TotalRatingRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final TotalRatingRepository totalRatingRepository;
    private final RecordOfRatingsRepository recordOfRatingsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponseDto getUserProfile(User user) {
        String userImg = user.getUserImg();
        String nickname = user.getNickname();
        String email = user.getEmail();

        Optional<TotalRating> checkUserTotalRating = totalRatingRepository.findByUserId(user.getUserId());
        if (checkUserTotalRating.isEmpty()){
            double totalManner = 4.0;
            double totalParticipation =4.0;
            double totalGamingSkill =4.0;
            double totalEnjoyable =4.0;
            double totalSociability =4.0;
            double totalRating =4.0;

            UserTotalRatingResponseDto userTotalRatingResponseDto = new UserTotalRatingResponseDto(
                    totalManner, totalParticipation,totalGamingSkill,totalEnjoyable,totalSociability,totalRating);

            UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(userImg, nickname, email, userTotalRatingResponseDto, 0L);
            return userProfileResponseDto;
        }

        double totalManner = checkUserTotalRating.get().getTotalManner();
        double totalParticipation =checkUserTotalRating.get().getTotalParticipation();
        double totalGamingSkill =checkUserTotalRating.get().getTotalGamingSkill();
        double totalEnjoyable =checkUserTotalRating.get().getTotalEnjoyable();
        double totalSociability =checkUserTotalRating.get().getTotalSociability();
        double totalRating =checkUserTotalRating.get().getTotalRating();

        UserTotalRatingResponseDto userTotalRatingResponseDto = new UserTotalRatingResponseDto(
                totalManner, totalParticipation,totalGamingSkill,totalEnjoyable,totalSociability,totalRating);

        Long numberOfEvaluations = recordOfRatingsRepository.countByUserId(user.getUserId());

        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(userImg, nickname, email, userTotalRatingResponseDto, numberOfEvaluations);
        return userProfileResponseDto;
    }

    @Transactional
    public void updateUserNickname(Long userId, CheckNicknameRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST, false));

        user.updateNickname(requestDto.getNickname());
    }

    public void checkUserPassword(User user, CheckPasswordRequestDto requestDto) {
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorMessage.PASSWORD_MISMATCH, HttpStatus.BAD_REQUEST, false);
        }
    }

    @Transactional
    public void updateUserPassword(Long userId, CheckPasswordRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST, false));

        String newPassword = passwordEncoder.encode(requestDto.getPassword());
        user.updatePassword(newPassword);
    }
}
