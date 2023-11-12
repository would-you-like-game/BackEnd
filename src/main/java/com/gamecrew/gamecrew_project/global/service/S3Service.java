package com.gamecrew.gamecrew_project.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileToS3(MultipartFile file, String dir) throws IOException {
        long size = file.getSize();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(size);
        objectMetadata.setContentDisposition("inline");

        String prefix = UUID.randomUUID().toString();
        String fileName = prefix + "_" + file.getOriginalFilename();
        String bucketFilePath = dir + "/" + fileName;

        InputStream inputStream = file.getInputStream();
        BufferedImage originalImage = ImageIO.read(inputStream);

        int targetWidth = 500; // 가로 길이를 500px로 설정

        String formatName = getFormatName(file);  // 원본 이미지의 확장자를 반환합니다.

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
            byte[] fileBytes = file.getBytes();
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

    public void deleteFileFromS3(String filePath) {
        amazonS3Client.deleteObject(bucketName, filePath);
    }

    public String getURLFromS3(String filePath) {
        return "https://s3.ap-northeast-2.amazonaws.com/" + bucketName + "/" + filePath;
    }
}
