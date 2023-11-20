package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.dto.request.UserRatingRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserTotalRatingResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.RecordOfRatings;
import com.gamecrew.gamecrew_project.domain.user.entity.TotalRating;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.RecordOfRatingsRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.TotalRatingRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final TotalRatingRepository totalRatingRepository;
    private final RecordOfRatingsRepository recordOfRatingsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registrationOfRatings(UserRatingRequestDto userRatingRequestDto, User evaluator, Long evaluated_user) {
        int manner = userRatingRequestDto.getManner();
        int participation = userRatingRequestDto.getParticipation();
        int gamingSkill = userRatingRequestDto.getGamingSkill();
        int enjoyable = userRatingRequestDto.getEnjoyable();
        int sociability = userRatingRequestDto.getSociability();
        Long evaluatorId =evaluator.getUserId();

        Optional<TotalRating> checkRating = totalRatingRepository.findByUserId(evaluated_user);
        LocalDateTime now = LocalDateTime.now();

        if (checkRating.isEmpty()){
            double totalRating = (double)(manner + participation + gamingSkill + enjoyable + sociability) / 5 ;


            RecordOfRatings ratings = new RecordOfRatings(evaluated_user ,evaluatorId, manner, participation, gamingSkill, enjoyable, sociability, totalRating, now);
            recordOfRatingsRepository.save(ratings);

            TotalRating totalrating = new TotalRating(evaluated_user, manner, participation, gamingSkill, enjoyable, sociability, totalRating);
            totalRatingRepository.save(totalrating);

        } else if (checkRating.isPresent()) {
            PageRequest pageable = PageRequest.of(0, 9);
            List<RecordOfRatings> beforeRatings = recordOfRatingsRepository.findTop9ByUserIdOrderByRecordedAtDesc(evaluated_user, pageable);

            double sumManner = manner;
            double sumParticipation = participation;
            double sumGamingSkill = gamingSkill;
            double sumEnjoyable = enjoyable;
            double sumSociability = sociability;

            for (RecordOfRatings rating : beforeRatings) {
                sumManner += rating.getManner();
                sumParticipation += rating.getParticipation();
                sumGamingSkill += rating.getGamingSkill();
                sumEnjoyable += rating.getEnjoyable();
                sumSociability += rating.getSociability();
            }

            double totalManner = sumManner / (1.0 + beforeRatings.size());
            double totalParticipation = sumParticipation / (1.0 + beforeRatings.size());
            double totalGamingSkill = sumGamingSkill / (1.0 + beforeRatings.size());
            double totalEnjoyable = sumEnjoyable / (1.0 + beforeRatings.size());
            double totalSociability = sumSociability / (1.0 + beforeRatings.size());
            double totalRating = (totalManner + totalParticipation + totalGamingSkill + totalEnjoyable + totalSociability) / 5;

            RecordOfRatings ratings = new RecordOfRatings(evaluated_user ,evaluatorId, manner, participation, gamingSkill, enjoyable, sociability, totalRating, now);
            recordOfRatingsRepository.save(ratings);

            // Update TotalRating
            TotalRating totalRatingEntity = checkRating.get();
            totalRatingEntity.setTotalManner(totalManner);
            totalRatingEntity.setTotalParticipation(totalParticipation);
            totalRatingEntity.setTotalGamingSkill(totalGamingSkill);
            totalRatingEntity.setTotalEnjoyable(totalEnjoyable);
            totalRatingEntity.setTotalSociability(totalSociability);
            totalRatingEntity.setTotalRating(totalRating);
            totalRatingRepository.save(totalRatingEntity);
        }
    }

    public UserTotalRatingResponseDto getUserRating(Long evaluated_user) {
        Optional<User> user= userRepository.findByUserId(evaluated_user);
        if (!user.isPresent()){
            throw new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST);
        }

        Optional<TotalRating> checkUser= totalRatingRepository.findByUserId(evaluated_user);
        if (checkUser.isEmpty())
        {
            double totalManner = 4.0;
            double totalParticipation =4.0;
            double totalGamingSkill =4.0;
            double totalEnjoyable =4.0;
            double totalSociability =4.0;
            double totalRating =4.0;

            return new UserTotalRatingResponseDto(totalManner, totalParticipation, totalGamingSkill, totalEnjoyable, totalSociability, totalRating);
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
