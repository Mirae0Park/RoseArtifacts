package com.roseArtifacts.dto;

import com.roseArtifacts.constant.GoodsSellStatus;
import com.roseArtifacts.entity.Goods;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsFormDto {

    private Long id;

    private String name; // 상품명

    private int price; // 가격

    private Integer stock; // 재고

    private String cate; // 카테고리

    private String description; // 상세 설명

    private GoodsSellStatus status; // 상품 판매 상태

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private List<GoodsImgDto> goodsImgDtoList = new ArrayList<>(); // 상품 저장 후 수정할 때 상품 이미지 정보를 저장

    private List<Long> goodsImgIds = new ArrayList<>(); // 상품 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Goods createGoods() {
        return modelMapper.map(this, Goods.class);
    }

    public static GoodsFormDto of (Goods goods) {
        return modelMapper.map(goods, GoodsFormDto.class);
    }

}
