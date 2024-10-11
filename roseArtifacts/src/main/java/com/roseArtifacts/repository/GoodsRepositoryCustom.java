package com.roseArtifacts.repository;

import com.roseArtifacts.dto.GoodsSearchDto;
import com.roseArtifacts.dto.MainGoodsDto;
import com.roseArtifacts.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsRepositoryCustom {

    List<MainGoodsDto> getMainGoodsPage(GoodsSearchDto goodsSearchDto);

    Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);
}
