package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.Orders.ReviewListDTO;
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
	
	// Mapping 주소로 front가 헤더에 토큰을 담아서 보내면 -> Spring Security의 JWTAuthorizationFilter가 이 토큰을 가로챔
	// JWTAuthorizationFilter가 토큰을 디코딩하고 -> username을 꺼내고-> User 객체를 만들고 -> 그걸 SecurityContextHolder에 등록해줌.
	// 그리고 그 결과로 Principal을 나에게 넘겨줌. 
	
	@GetMapping("/recommend")
	public ResponseEntity<?> getRecommendGoods(Principal principal) {
		// SecurityContext에 주입되는 principal(첫 번째 인자) => User(Spring Security의 User) 타입
		// 토큰 인증을 받아야 함으로 이렇게 써야함. 대신 User user 쓸 때는 id와 인가 정보 외에는 없음.
		System.out.println("[진입] : [GoodsController] 추천 상품 진입 ");
		System.out.println(principal.getName());
		String username = principal.getName();
	    return ResponseEntity.ok(goodsService.getRecommendGoods(username));
	}
	
	
	@GetMapping("/reviews/product/{imgname}")
	public List<ReviewListDTO> getProductReviews(@PathVariable String imgname) {
		System.out.println("[진입] : [GoodsController] 상품 리뷰 조회 진입 ");
		List<ReviewListDTO> review = goodsService.getReviewsByImgname(imgname);
		System.out.println("[성공] : [GoodsController] 상품 리뷰 조회 성공 ");
	    return review;
	}



}
