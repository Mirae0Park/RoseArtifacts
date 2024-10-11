package com.roseArtifacts.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.roseArtifacts.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainGoodsDto {

    private Long id;

    private String name;

    private String description;

    private String imgUrl;

    private Integer price;

    private String cate;

    private Integer stock;

    private GoodsSellStatus status;

    @QueryProjection
    public MainGoodsDto(Long id, String name, String description, String imgUrl,
                        Integer price, String cate, Integer stock, GoodsSellStatus status){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.cate = cate;
        this.stock = stock;
        this.status = status;
    }
}
