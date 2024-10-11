package com.roseArtifacts.repository;


import com.roseArtifacts.dto.GoodsSearchDto;
import com.roseArtifacts.dto.MainGoodsDto;
import com.roseArtifacts.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>, QuerydslPredicateExecutor<Goods>, GoodsRepositoryCustom {


}
