package com.roseArtifacts;

import com.roseArtifacts.service.FileService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {

    // 이미지 업로드 검증 메서드
//    public static void main(String[] args) {
//        try {
//            // 테스트용 이미지 파일을 로드 (경로에 있는 이미지를 byte[]로 변환)
//            Path imagePath = Paths.get("C:/upload/goodsImages/10fca27b-dd20-4354-a138-c0b6e07605a4.jpg");
//            byte[] imageData = Files.readAllBytes(imagePath);
//
//            // 업로드 경로 설정 (예시)
//            String uploadPath = "C:/upload/goodsImages/";
//
//            // 파일 업로드 및 저장 메서드 호출
//            FileService fileUploadService = new FileService();
//
//            // 파일 저장
//            String savedFileName = fileUploadService.uploadFile(uploadPath, "test-image.jpg", imageData);
//
//            // 썸네일 저장
//            String savedThumbnail = fileUploadService.saveThumbnail(uploadPath, "test-image.jpg", imageData);
//
//            // 업로드된 파일 경로 확인
//            File uploadedFile = new File(uploadPath + "/" + savedFileName);
//            File thumbnailFile = new File(uploadPath + "/" + savedThumbnail);
//
//            // 파일이 실제로 저장되었는지 확인
//            if (uploadedFile.exists() && thumbnailFile.exists()) {
//                System.out.println("파일이 성공적으로 저장되었습니다: " + uploadedFile.getAbsolutePath());
//                System.out.println("썸네일이 성공적으로 저장되었습니다: " + thumbnailFile.getAbsolutePath());
//            } else {
//                System.out.println("파일 저장 실패");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void convertToWebpWithLosslessTEST() throws Exception {

        FileService fileUploadService = new FileService();

        // given
        String testFileName = "F_LOUNGE.png"; // 테스트 파일 명
        File testFile = new File(testFileName);

        // when
        File convertedFile = fileUploadService.convertToWebpWithLossless("F_LOUNGE_LOSSLESS", testFile);

        // then
        double originalFileSizeKB = testFile.length() / 1024.0;
        double convertedFileSizeKB = convertedFile.length() / 1024.0;
        // 압축 비율 계산 (백분율)
        double compressionRatio = (convertedFileSizeKB / originalFileSizeKB) * 100;
        // 압축률 계산
        double compressionRate = 100 - compressionRatio;

        System.out.printf("Original File Size: %.2f KB%n", originalFileSizeKB);
        System.out.printf("Converted File Size: %.2f KB%n", convertedFileSizeKB);
        System.out.printf("Compression Rate: %.2f%%%n", compressionRate);

        assertTrue(convertedFile.exists(), "Converted file should exist");
    }
}
