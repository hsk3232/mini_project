package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	
	@GetMapping("/recommend")
	public ResponseEntity<?> getRecommendGoods(Principal principal) {
		// SecurityContext에 주입되는 principal(첫 번째 인자) => User(Spring Security의 User) 타입
		// 토큰 인증을 받아야 함으로 이렇게 써야함. 대신 User user 쓸 때는 id와 인가 정보 외에는 없음.
		System.out.println("[진입] : [GoodsController] 추천 상품 진입 ");
		System.out.println(principal.getName());
		String username = principal.getName();
	    return ResponseEntity.ok(goodsService.getRecommendGoods(username));
	}
	


}
