package edu.pnu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.service.everyone.GoodsDetailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class GoodsDetailController {

	private final GoodsDetailService goodsDetailService;
	
	@GetMapping("/detail/{imgname}")
	public ResponseEntity<?> getGoodsDetail(@PathVariable String imgname) {
	    System.out.println("[진입] : [상품 상세 페이지 진입]");
	    try {
	        GoodsDTO dto = goodsDetailService.getGoodsDetail(imgname);
	        System.out.println("[성공] : [상품 상세 페이지 조회 완료]" + "\n");
	        return ResponseEntity.ok(dto);
	    } catch (IllegalArgumentException e) {
	        System.out.println("[오류 / 실패] : [상품 상세 페이지 조회 불가]" + e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(e.getMessage()); // 메시지만 내려줌
	        
	    }
	}
	
	
}
