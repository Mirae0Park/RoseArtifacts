package com.roseArtifacts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goods_img")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "goods")
public class GoodsImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgnew; // 이미지 파일명

    private String imgori; // 원본 이미지 파일명

    private String imgurl; // 이미지 조회 경로

    private String imgrep; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public void updateItemImg(String imgori, String imgnew, String imgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.imgori = imgori;
        this.imgnew = imgnew;
        this.imgurl = imgurl;
    }
}
