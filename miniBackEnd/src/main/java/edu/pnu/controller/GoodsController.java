package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Goods;
import edu.pnu.persistence.GoodsRepository;

@RestController
@RequestMapping("/api/public")
public class GoodsController {
	
	@Autowired
	private GoodsRepository goodsRepo;
	
	@GetMapping("/goods")
	public List<Goods> getGoods() {
		
		return goodsRepo.findAll();
	}
	
	
}
