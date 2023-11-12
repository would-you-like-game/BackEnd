package com.gamecrew.gamecrew_project.domain.post.service;

import com.gamecrew.gamecrew_project.domain.post.entity.PostImg;
import com.gamecrew.gamecrew_project.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostS3Service {

    private final S3Service s3Service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    //이미지를 S3에 업로드하고, PostImg 객체를 생성하여 리스트에 추가
    public List<PostImg> uploadPhotosToS3AndCreatePostImages(List<MultipartFile> photos) throws IOException {
        List<PostImg> postImgList = new ArrayList<>();

        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) { // 파일이 비어있지 않은 경우에만 업로드 진행
                String fileName = s3Service.uploadFileToS3(photo, "post_img");
                String url = s3Service.getURLFromS3("post_img/" + fileName);
                PostImg postImg = new PostImg(url, null);
                postImgList.add(postImg);
            }
        }
        return postImgList;
    }
}
