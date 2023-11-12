package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserS3Service {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void updateUserImage(Long userId, MultipartFile userImg) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST));

        // 이전에 이미지가 존재하면 삭제
        if (user.getUserImg() != null) {
            String oldFileName = user.getUserImg().substring(user.getUserImg().lastIndexOf("/") + 1);
            s3Service.deleteFileFromS3("user_image/" + oldFileName);
        }

        String fileName = s3Service.uploadFileToS3(userImg, "user_image");

        // S3에서 이미지 URL 가져오기
        String imageUrl = s3Service.getURLFromS3("user_image/" + fileName);

        user.updateUserImg(imageUrl);
        userRepository.save(user);
    }
}