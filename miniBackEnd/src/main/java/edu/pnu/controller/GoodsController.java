package edu.pnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.goods.AdGoodsDTO;
import edu.pnu.service.everyone.GoodsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class GoodsController {
	
	private final GoodsService goodsService;
	
	@GetMapping("/popular")
	public AdGoodsDTO getPopularGoods() {
		
		return goodsService.getPopularGoods(); 
	}
	
	@GetMapping("/recommend")
	public AdGoodsDTO getRecommendGoods(String keyword) {
		return goodsService.getPopularGoods(); 
	}
	
}
