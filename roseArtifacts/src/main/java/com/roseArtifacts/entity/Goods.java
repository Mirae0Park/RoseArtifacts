package com.roseArtifacts.entity;

import com.roseArtifacts.constant.GoodsSellStatus;
import com.roseArtifacts.dto.GoodsFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goods")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 상품명

    private int price; // 가격

    private Integer stock; // 재고

    private String cate; // 카테고리

    @Lob
    private String description; // 상세 설명

    @Enumerated(EnumType.STRING)
    private GoodsSellStatus status; // 판매 상태

    public void updateGoods(GoodsFormDto goodsFormDto){
        this.name = goodsFormDto.getName();
        this.price = goodsFormDto.getPrice();
        this.cate = goodsFormDto.getCate();
        this.stock = goodsFormDto.getStock();
        this.description = goodsFormDto.getDescription();
        this.status = goodsFormDto.getStatus();
    }

}
