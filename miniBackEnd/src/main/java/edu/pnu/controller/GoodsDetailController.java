package edu.pnu.controller;

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
	public GoodsDTO getGoodsDetail(@PathVariable String imgname) {
	    return goodsDetailService.getGoodsDetail(imgname);
	}
	
}
