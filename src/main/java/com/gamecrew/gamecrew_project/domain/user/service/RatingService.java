package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.dto.request.UserRatingRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserTotalRatingResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.RecordOfRatings;
import com.gamecrew.gamecrew_project.domain.user.entity.TotalRating;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.TotalRatingRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.RecordOfRatingsRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final UserRepository userRepository;
    private final TotalRatingRepository totalRatingRepository;
    private final RecordOfRatingsRepository recordOfRatingsRepository;
    public void registrationOfRatings(UserRatingRequestDto userRatingRequestDto, User evaluator, Long evaluated_user) {
        int manner = userRatingRequestDto.getManner();
        int participation = userRatingRequestDto.getParticipation();
        int gamingSkill = userRatingRequestDto.getGamingSkill();
        int enjoyable = userRatingRequestDto.getEnjoyable();
        int sociability = userRatingRequestDto.getSociability();
        Long evaluatorId =evaluator.getUserId();

        Optional<TotalRating> checkRating = totalRatingRepository.findByEvaluatedUserId(evaluated_user);
        if (checkRating.isEmpty()){
            double totalRating = (double)(manner + participation + gamingSkill + enjoyable + sociability) / 5 ;

            RecordOfRatings ratings = new RecordOfRatings(evaluated_user ,evaluatorId, manner, participation, gamingSkill, enjoyable, sociability, totalRating);
            recordOfRatingsRepository.save(ratings);

            TotalRating totalrating = new TotalRating(evaluated_user, manner, participation, gamingSkill, enjoyable, sociability, totalRating);
            totalRatingRepository.save(totalrating);

        } else if (checkRating.isPresent()) {
            TotalRating existingTotalRating = checkRating.get();

            //있으면 저장된 점수 + 새로 들어온 점수 더하기 + /2
            double totalManner = (existingTotalRating.getTotalManner() + manner)/2;
            double totalParticipation = (existingTotalRating.getTotalParticipation() + participation)/2;
            double totalGamingSkill = (existingTotalRating.getTotalGamingSkill() + gamingSkill)/2;
            double totalEnjoyable =(existingTotalRating.getTotalEnjoyable() + enjoyable)/2;
            double totalSociability = (existingTotalRating.getTotalSociability() + sociability)/2;
            double total =(totalManner + totalParticipation + totalGamingSkill + totalEnjoyable + totalSociability)/5;

            existingTotalRating.setTotalManner(totalManner);
            existingTotalRating.setTotalParticipation(totalParticipation);
            existingTotalRating.setTotalGamingSkill(totalGamingSkill);
            existingTotalRating.setTotalEnjoyable(totalEnjoyable);
            existingTotalRating.setTotalSociability(totalSociability);
            existingTotalRating.setTotalRating(total);

            double totalRating = (double)(manner + participation + gamingSkill + enjoyable + sociability) / 5 ;
            RecordOfRatings ratings = new RecordOfRatings(evaluated_user ,evaluatorId, manner, participation, gamingSkill, enjoyable, sociability, totalRating);
            recordOfRatingsRepository.save(ratings);
        }
    }
    public UserTotalRatingResponseDto getUserRating(Long evaluated_user) {
        Optional<TotalRating> checkUser= totalRatingRepository.findByEvaluatedUserId(evaluated_user);
        if (checkUser.isEmpty()){
            throw new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST, false);
        }
        TotalRating existingTotalRating = checkUser.get();
        double totalManner = existingTotalRating.getTotalManner();
        double totalParticipation = existingTotalRating.getTotalParticipation();
        double totalGamingSkill = existingTotalRating.getTotalGamingSkill();
        double totalEnjoyable =existingTotalRating.getTotalEnjoyable();
        double totalSociability = existingTotalRating.getTotalSociability();
        double total = existingTotalRating.getTotalRating();
        return new UserTotalRatingResponseDto(totalManner, totalParticipation, totalGamingSkill, totalEnjoyable, totalSociability, total);
    }
}
