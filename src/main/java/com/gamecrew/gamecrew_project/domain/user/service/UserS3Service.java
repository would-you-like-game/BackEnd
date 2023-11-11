package com.gamecrew.gamecrew_project.domain.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserS3Service {
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public void updateUserImage(Long userId, MultipartFile userImg) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorMessage.NON_EXISTENT_USER, HttpStatus.BAD_REQUEST));

        long size = userImg.getSize();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(userImg.getContentType());
        objectMetadata.setContentLength(size);
        objectMetadata.setContentDisposition("inline");

        String prefix = UUID.randomUUID().toString();
        String fileName = prefix + "_" + userImg.getOriginalFilename();
        String bucketFilePath = "user_image/" + fileName;

        // 이전에 이미지가 존재하면 삭제
        if (user.getUserImg() != null) {
            String oldFileName = user.getUserImg().substring(user.getUserImg().lastIndexOf("/") + 1);
            amazonS3Client.deleteObject(bucketName, "user_image/" + oldFileName);
        }

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, bucketFilePath, userImg.getInputStream(), objectMetadata));
        } catch (AmazonS3Exception e) {
            // S3 업로드 실패 시 처리 로직
            throw new RuntimeException("S3 업로드 실패", e);
        }

        // S3에서 이미지 URL 가져오기
        String imageUrl = amazonS3Client.getUrl(bucketName, bucketFilePath).toString();

        // 사용자의 이미지 URL 업데이트
        user.updateUserImg(imageUrl);

        // 변경 사항 저장
        userRepository.save(user);
    }
}
