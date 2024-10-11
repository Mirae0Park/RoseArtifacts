package com.roseArtifacts.controller;

import com.roseArtifacts.dto.GoodsFormDto;
import com.roseArtifacts.dto.GoodsSearchDto;
import com.roseArtifacts.dto.MainGoodsDto;
import com.roseArtifacts.entity.Goods;
import com.roseArtifacts.repository.MemberRepository;
import com.roseArtifacts.service.GoodsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GoodsController {

    private final MemberRepository memberRepository;
    private final GoodsService goodsService;

    @GetMapping(value = "goods/register")
    public String goodsRegister(Model model){
        model.addAttribute("goodsFormDto", new GoodsFormDto());
        return "goods/goodsRegister";
    }

    @PostMapping(value = "goods/register")
    public String goodsNew(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("goodsImgFile") List<MultipartFile> goodsImgFileList){

        if(bindingResult.hasErrors()){
            return "goods/goodsRegister";
        }

        if(goodsImgFileList.get(0).isEmpty() && goodsFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "goods/goodsRegister";
        }

        try {
            goodsService.saveItem(goodsFormDto, goodsImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "goods/goodsRegister";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/goods", "/admin/goods/{page}"})
    public String adminPage(GoodsSearchDto goodsSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Goods> goods = goodsService.getAdminGoodsPage(goodsSearchDto, pageable);
        model.addAttribute("goods", goods);
        model.addAttribute("goodsSearchDto", goodsSearchDto);
        model.addAttribute("maxPage", 5);

        return "goods/goodsMng";
    }

    @GetMapping(value = "/goods/list")
    public String goodsList(@RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "cate", required = false) String cate, Model model){

        GoodsSearchDto goodsSearchDto = new GoodsSearchDto();

        if(name != null && !name.isEmpty()) {
            goodsSearchDto.setGoodsName(name);
        }

        if(cate != null && !cate.isEmpty()) {
            goodsSearchDto.setCate(cate);
        }

        List<MainGoodsDto> goods = goodsService.getMainGoods(goodsSearchDto);

        model.addAttribute("goods", goods);

        return "goods/goods";
    }

    @GetMapping(value = "/admin/goodsDtl/{goodsId}")
    public String goodsDtl(@PathVariable("goodsId") Long goodsId, Model model) {
        try {
            GoodsFormDto goodsFormDto = goodsService.getItemDtl(goodsId);
            model.addAttribute("goodsFormDto", goodsFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("goodsFormDto", new GoodsFormDto());

            return "goods/goodsRegister";
        }
        return "goods/goodsRegister";

    }

    @GetMapping(value = "/goods/list/async", produces = "application/json")
    @Async
    @ResponseBody
    public CompletableFuture<List<MainGoodsDto>> asyncGoodsList(@RequestParam(name = "cate", required = false) String cate) {

        GoodsSearchDto goodsSearchDto = new GoodsSearchDto();

        if (cate != null && !cate.isEmpty()) {
            goodsSearchDto.setCate(cate);
        }

        List<MainGoodsDto> goods = goodsService.getMainGoods(goodsSearchDto);

        return CompletableFuture.completedFuture(goods);
    }

    @PostMapping(value = "/admin/goods/{ItemId}")
    public String goodsUpdate(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult,
                             @RequestParam("goodsImgFile") List<MultipartFile> goodsImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "goods/goodsRegister";
        }

        if(goodsImgFileList.get(0).isEmpty() && goodsFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "goods/goodsRegister";
        }

        try {
            goodsService.updateItem(goodsFormDto, goodsImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "goods/goodsRegister";
        }

        return "redirect:/admin/goods";
    }

    @GetMapping(value = "/goods/detail/{goodsId}")
    public String productDetail(Model model, @PathVariable("goodsId") Long goodsId, Principal principal){

        GoodsFormDto goodsFormDto = goodsService.getItemDtl(goodsId);

        if (goodsFormDto == null) {
            return "redirect:/";
        }

        model.addAttribute("goods", goodsFormDto);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }

        return "goods/goodsDetail";

    }
}
