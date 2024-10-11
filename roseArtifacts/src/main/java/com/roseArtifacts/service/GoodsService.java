package com.roseArtifacts.service;

import com.roseArtifacts.dto.GoodsFormDto;
import com.roseArtifacts.dto.GoodsImgDto;
import com.roseArtifacts.dto.GoodsSearchDto;
import com.roseArtifacts.dto.MainGoodsDto;
import com.roseArtifacts.entity.Goods;
import com.roseArtifacts.entity.GoodsImg;
import com.roseArtifacts.repository.GoodsImgRepository;
import com.roseArtifacts.repository.GoodsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class GoodsService {

    private final GoodsRepository goodsRepository;

    private final GoodsImgService goodsImgService;

    private final GoodsImgRepository goodsImgRepository;

    public Long saveItem(GoodsFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록
        Goods goods = itemFormDto.createGoods();
        goodsRepository.save(goods);

        log.info("ItemService : " + goods);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            GoodsImg goodsImg = new GoodsImg();
            goodsImg.setGoods(goods);
            if(i == 0) {
                goodsImg.setImgrep("Y");
                log.info("repImgYn Y");
            }
            else {
                goodsImg.setImgrep("N");
                log.info("repImgYn N");
            }
            goodsImgService.saveGoodsImg(goodsImg, itemImgFileList.get(i));
        }

        return goods.getId();
    }

    @Transactional(readOnly = true)
    public GoodsFormDto getItemDtl(Long goodsId){
        List<GoodsImg> goodsImgList = goodsImgRepository.findByGoodsIdOrderByIdAsc(goodsId); // 해당 상품의 이미지 조회

        List<GoodsImgDto> goodsImgDtoList = new ArrayList<>();

        for(GoodsImg goodsImg : goodsImgList){
            GoodsImgDto itemImgDto = GoodsImgDto.of(goodsImg);
            goodsImgDtoList.add(itemImgDto);
        }

        Goods goods = goodsRepository.findById(goodsId).orElseThrow(EntityNotFoundException::new); // 상품의 아이디를 통해 상품 엔티티를 조회

        GoodsFormDto goodsFormDto = GoodsFormDto.of(goods);
        goodsFormDto.setGoodsImgDtoList(goodsImgDtoList);

        return goodsFormDto;
    }

    public Long updateItem(GoodsFormDto goodsFormDto, List<MultipartFile> goodsImgFileList) throws Exception{

        log.info("ItemService updateItem..........");
        log.info("ItemFormDto get Id : " + goodsFormDto.getId());
        //상품 수정
        Goods goods = goodsRepository.findById(goodsFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        goods.updateGoods(goodsFormDto);
        List<Long> itemImgIds = goodsFormDto.getGoodsImgIds();

        //이미지 등록
        for(int i=0;i<goodsImgFileList.size();i++){
            goodsImgService.updateGoodsImg(itemImgIds.get(i),
                    goodsImgFileList.get(i));
        }

        return goods.getId();
    }

    @Transactional(readOnly = true)
    public List<MainGoodsDto> getMainGoods(GoodsSearchDto goodsSearchDto) {
        return goodsRepository.getMainGoodsPage(goodsSearchDto);
    }

    @Transactional(readOnly = true)
    public Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        return goodsRepository.getAdminGoodsPage(goodsSearchDto, pageable);
    }
}
