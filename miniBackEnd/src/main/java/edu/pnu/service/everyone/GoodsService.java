package edu.pnu.service.everyone;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.dto.goods.AdGoodsDTO;
import edu.pnu.dto.goods.PopularResponseDTO;
import edu.pnu.dto.goods.RecommendResponseDTO;
import edu.pnu.persistence.GoodsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsService {

	private final GoodsRepository goodsRepo;

	public PopularResponseDTO getPopularGoods() {

		// 3. 인기 상품 (조회수 Top 10)
		List<Goods> popularGoods = goodsRepo.findTop10ByOrderByViewcountDesc();
		List<AdGoodsDTO> popularItems = new ArrayList<>();
		for (Goods g : popularGoods) {
			popularItems.add(AdGoodsDTO.fromEntity(g));
		}
		return AdGoodsDTO.builder().popularItems(popularItems).build();
	}

	public RecommendResponseDTO getRecommendGoods(String keyword) {
		// 4. 추천 상품 (키워드 포함)
		List<Goods> recommendedGoods = goodsRepo.findTop10ByProductNameContainingIgnoreCase(keyword);
		List<AdGoodsDTO> recommendedItems = new ArrayList<>();
		for (Goods g : recommendedGoods) {
			recommendedItems.add(AdGoodsDTO.fromEntity(g));
		}
		return recommendedItems

	}

}
