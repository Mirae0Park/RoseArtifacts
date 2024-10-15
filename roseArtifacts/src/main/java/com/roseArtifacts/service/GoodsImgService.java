package com.roseArtifacts.service;

import com.roseArtifacts.entity.GoodsImg;
import com.roseArtifacts.repository.GoodsImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class GoodsImgService {

    @Value("${goodsImgLocation}")
    private String goodsImgLocation;

    private final GoodsImgRepository goodsImgRepository;

    private final FileService fileService;

    public void saveGoodsImg(GoodsImg goodsImg, MultipartFile goodsImgFile) throws Exception {
        String oriImgName = goodsImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {

            // 대표 이미지를 썸네일로 만들어서 저장
            if (goodsImg.getImgrep().equals("Y")) {
                imgName = fileService.saveThumbnail(goodsImgLocation, oriImgName, goodsImgFile.getBytes());
            }

            // 일반 이미지 저장
            if (goodsImg.getImgrep().equals("N")) {
                imgName = fileService.uploadFile(goodsImgLocation, oriImgName, goodsImgFile.getBytes());
            }

            imgUrl = "/images/goodsImages/" + imgName;
        }

        // 상품 이미지 정보 저장
        goodsImg.updateItemImg(oriImgName, imgName, imgUrl);
        goodsImgRepository.save(goodsImg);

    }

    public void updateGoodsImg(Long goodsImgId, MultipartFile goodsImgFile) throws Exception{
        if(!goodsImgFile.isEmpty()) { // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            GoodsImg savedGoodsImg = goodsImgRepository.findById(goodsImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedGoodsImg.getImgnew())) {
                fileService.deleteFile(goodsImgLocation+"/"+
                        savedGoodsImg.getImgnew());
            }

            String imgori = goodsImgFile.getOriginalFilename();
            String imgnew = fileService.uploadFile(goodsImgLocation, imgori, goodsImgFile.getBytes());
            String imgurl = "/images/goodsImages/" + imgnew;
            savedGoodsImg.updateItemImg(imgori, imgnew, imgurl);
        }
    }

}
