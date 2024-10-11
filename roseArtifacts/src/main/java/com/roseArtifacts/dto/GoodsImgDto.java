package com.roseArtifacts.dto;

import com.roseArtifacts.entity.GoodsImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsImgDto {

    private Long id;

    private String imgnew; // 이미지 파일명

    private String imgori; // 원본 이미지 파일명

    private String imgurl; // 이미지 조회 경로

    private String imgrep; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static GoodsImgDto of (GoodsImg goodsImg) {
        return modelMapper.map(goodsImg, GoodsImgDto.class);
    }
}
