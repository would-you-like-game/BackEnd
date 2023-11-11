package com.gamecrew.gamecrew_project.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gamecrew.gamecrew_project.domain.post.entity.PostImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    //이미지를 S3에 업로드하고, PostImg 객체를 생성하여 리스트에 추가
    public List<PostImg> uploadPhotosToS3AndCreatePostImages(List<MultipartFile> photos) throws IOException {

        List<PostImg> postImgList = new ArrayList<>();

        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) { // 파일이 비어있지 않은 경우에만 업로드 진행
                String fileName = uploadPhotoToS3AndGetFileName(photo);
                PostImg postImg = new PostImg(fileNameToURL(fileName), null);
                postImgList.add(postImg);
            }
        }
        return postImgList;
    }

    //S3에 이미지를 업로드하고, 업로드된 파일의 이름을 반환
    private String uploadPhotoToS3AndGetFileName(MultipartFile photo) throws IOException {
        long size = photo.getSize();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(photo.getContentType());
        objectMetadata.setContentLength(size);
        objectMetadata.setContentDisposition("inline");

        String prefix = UUID.randomUUID().toString();
        String fileName = prefix + "_" + photo.getOriginalFilename();
        String bucketFilePath = "post_image/" + fileName;

        InputStream inputStream = photo.getInputStream();

        BufferedImage originalImage = ImageIO.read(inputStream);

        int targetWidth = 500; // 가로 길이를 500px로 설정

        String formatName = getFormatName(photo);  // 원본 이미지의 확장자를 반환합니다.

        // 이미지가 500px보다 크면 리사이즈
        if (originalImage.getWidth() > targetWidth) {
            BufferedImage resizedImage = resizeImage(originalImage, targetWidth);

            // 리사이즈된 이미지를 S3에 업로드
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, formatName, outputStream);
            byte[] resizedImageBytes = outputStream.toByteArray();

            objectMetadata.setContentLength(resizedImageBytes.length);

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, bucketFilePath, new ByteArrayInputStream(resizedImageBytes), objectMetadata)
            );
        } else {
            // 여기서 inputStream 을 그대로 사용하기 보다 byte array 로 변환하여 사용
            byte[] fileBytes = photo.getBytes();
            objectMetadata.setContentLength(fileBytes.length);

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, bucketFilePath, new ByteArrayInputStream(fileBytes), objectMetadata)
            );
        }

        return fileName;
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        double aspectRatio = (double) originalImage.getHeight() / originalImage.getWidth();
        int targetHeight= (int) (targetWidth * aspectRatio);

        BufferedImage scaledImge= new BufferedImage(targetWidth,targetHeight ,BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d= scaledImge.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage ,0 ,0 ,targetWidth,targetHeight,null );

        g2d.dispose();

        return scaledImge;
    }

    private String getFormatName(MultipartFile photo) {
        String originalFileName = photo.getOriginalFilename();
        int lastDotIndex = originalFileName.lastIndexOf(".");

        // 확장자가 없거나 파일 이름이 '.'으로 시작하는 경우를 처리
        if (lastDotIndex == -1 || lastDotIndex == 0) {
            return null; // 또는 기본 포맷을 반환
        }

        return originalFileName.substring(lastDotIndex + 1);
    }

    private String fileNameToURL(String fileName) {
        return "https://gamecrew-cicd-bucket.s3.ap-northeast-2.amazonaws.com/post_image/" + fileName;
    }
}
