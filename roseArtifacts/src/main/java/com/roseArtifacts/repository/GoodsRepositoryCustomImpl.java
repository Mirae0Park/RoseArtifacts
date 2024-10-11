package com.roseArtifacts.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roseArtifacts.constant.GoodsSellStatus;
import com.roseArtifacts.dto.GoodsSearchDto;
import com.roseArtifacts.dto.MainGoodsDto;
import com.roseArtifacts.dto.QMainGoodsDto;
import com.roseArtifacts.entity.Goods;
import com.roseArtifacts.entity.QGoods;
import com.roseArtifacts.entity.QGoodsImg;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{

    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용

    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌
    public GoodsRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression nameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QGoods.goods.name.like("%" + searchQuery + "%");
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QGoods.goods.regTime.after(dateTime);
    }

    private BooleanExpression searchSellStatusEq(GoodsSellStatus goodsSellStatus){
        // 상품 판매 상태 조건이 전체일 경우에는 null을 리턴, 결과값이 null이면 where절에서 해당 조건을 무시됨
        return goodsSellStatus == null ? null : QGoods.goods.status.eq(goodsSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("title", searchBy)){
            return QGoods.goods.name.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QGoods.goods.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public List<MainGoodsDto> getMainGoodsPage(GoodsSearchDto goodsSearchDto) {
        QGoods goods = QGoods.goods;
        QGoodsImg goodsImg = QGoodsImg.goodsImg;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(goodsSearchDto.getGoodsName() != null && !goodsSearchDto.getGoodsName().isEmpty()){
            booleanBuilder.and(goods.name.contains(goodsSearchDto.getGoodsName()));
        }

        if(goodsSearchDto.getCate() != null && !goodsSearchDto.getCate().isEmpty()){
            booleanBuilder.and(goods.cate.eq(goodsSearchDto.getCate()));
        }

        booleanBuilder.and(goods.status.ne(GoodsSellStatus.valueOf(GoodsSellStatus.PAUSE.name())));

        List<MainGoodsDto> content = queryFactory
                .select(
                        new QMainGoodsDto(
                                goods.id,
                                goods.name,
                                goods.description,
                                goodsImg.imgurl,
                                goods.price,
                                goods.cate,
                                goods.stock,
                                goods.status
                        )
                )
                .from(goodsImg)
                .join(goodsImg.goods, goods)
                .where(goodsImg.imgrep.eq("y"), booleanBuilder)
                .where(nameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(goods.id.desc())
                .fetch();


        return content;
    }

    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {

        List<Goods> content = queryFactory
                .selectFrom(QGoods.goods)
                .where(regDtsAfter(goodsSearchDto.getSearchDateType()),
                        searchSellStatusEq(goodsSearchDto.getSearchSellStatus()),
                        searchByLike(goodsSearchDto.getSearchBy(),goodsSearchDto.getSearchQuery()))
                .orderBy(QGoods.goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QGoods.goods)
                .where(regDtsAfter(goodsSearchDto.getSearchDateType()),
                        searchSellStatusEq(goodsSearchDto.getSearchSellStatus()),
                        searchByLike(goodsSearchDto.getSearchBy(),
                                goodsSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }
}
