package edu.pnu.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.dto.GoodsDTO;
import edu.pnu.persistence.GoodsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsService {
	
	private final GoodsRepository goodsRepo;
	
	public List<GoodsDTO> findGoods(String main, String mid, String detail) {
	    List<Goods> goods;

	    if (detail != null) {
	        goods = goodsRepo.findByMainAndMidAndDetail(main, mid, detail);
	    } else if (mid != null) {
	        goods = goodsRepo.findByMainAndMid(main, mid);
	    } else {
	        goods = goodsRepo.findByMain(main);
	    }

	    return goods.stream().map(GoodsDTO::from).toList();
	}
	


}
