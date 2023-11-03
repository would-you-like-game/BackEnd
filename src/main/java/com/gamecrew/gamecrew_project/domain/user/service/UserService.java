package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.dto.response.UserProfileResponseDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserTotalRatingResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.TotalRating;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.TotalRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final TotalRatingRepository totalRatingRepository;

    public UserProfileResponseDto getUserProfile(User user) {
        String userImg = user.getUserImg();
        String nickname = user.getNickname();
        String email = user.getEmail();

        Optional<TotalRating> checkUserTotalRating = totalRatingRepository.findByEvaluatedUserId(user.getUserId());
        if (checkUserTotalRating.isEmpty()){
            UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(userImg, nickname, email, null);
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

        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(userImg, nickname, email, userTotalRatingResponseDto);
        return userProfileResponseDto;
    }
}
