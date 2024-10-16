package com.roseArtifacts.service;

import com.luciad.imageio.webp.WebPWriteParam;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID(); // 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용
        String savedFileName = uuid.toString() + ".webp";
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        BufferedImage originalImage;

        try {
            originalImage = ImageIO.read(new ByteArrayInputStream(fileData));
            log.info("Image successfully loaded from byte array.");
        } catch (IOException e) {
            log.error("Failed to load image from byte array", e);
            throw e;
        }

        // WebP로 저장
        File webpFile = new File(fileUploadFullUrl);
        try {
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            writer.setOutput(new FileImageOutputStream(webpFile));

            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
            writeParam.setCompressionQuality(0.8f);

            writer.write(null, new IIOImage(originalImage, null, null), writeParam);
            writer.dispose();

            log.info("Image successfully saved as WebP at: " + fileUploadFullUrl);
        } catch (Exception e) {
            log.error("Failed to save image as WebP", e);
            throw e;
        }

        return savedFileName;
    }

    public String saveThumbnail(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID(); // 서로 다른 개체들을 구별하기 위해 이름을 부여함
        String savedFileName = uuid.toString() + ".webp"; // WebP 확장자로 저장될 파일 이름을 생성
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new ByteArrayInputStream(fileData));
            log.info("Image successfully loaded from byte array.");
        } catch (IOException e) {
            log.error("Failed to load image from byte array", e);
            throw e;
        }

        // WebP로 저장
        File webpFile = new File(fileUploadFullUrl);
        try {
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            writer.setOutput(new FileImageOutputStream(webpFile));

            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
            writeParam.setCompressionQuality(0.8f);

            writer.write(null, new IIOImage(originalImage, null, null), writeParam);
            writer.dispose();

            log.info("Image successfully saved as WebP at: " + fileUploadFullUrl);
        } catch (Exception e) {
            log.error("Failed to save image as WebP", e);
            throw e;
        }

        return savedFileName;
    }


    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
