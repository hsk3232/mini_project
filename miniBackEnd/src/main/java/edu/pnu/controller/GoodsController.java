package edu.pnu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		System.out.println("[진입] : [GoodsController] 인기 상품 진입");
		try {

			 System.out.println("[완료] : [GoodsController] 인기 상품 정렬 완료");

			return goodsService.getPopularGoods();
		} catch (Exception e) {
			System.out.println("[오류] : [SearchHistoryService] 인기 상품 로딩 실패 \n");
			return null;
		}
	}


}
