package com.roseArtifacts.dto;

import com.roseArtifacts.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsSearchDto {
    
    private String goodsName;
    
    private String cate;
    
    private String searchDateType; // 상품 등록일 비교
    
    private GoodsSellStatus searchSellStatus; // 판매 상태 비교
    
    private String searchBy; // 어떤 유형으로 조회할 지
    
    private String searchQuery = ""; // 조회할 검색어 저장할 변수
}
