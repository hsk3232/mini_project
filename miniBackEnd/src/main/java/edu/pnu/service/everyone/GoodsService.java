package edu.pnu.service.everyone;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.dto.goods.AdGoodsDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.persistence.SearchHistoryRepository;
import edu.pnu.util.GoodsImgUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsService {

	private final GoodsRepository goodsRepo;
	private final SearchHistoryRepository searchHistoryRepo;
	
	// 1. 인기 상품 (조회수 Top 10)
	public AdGoodsDTO getPopularGoods() {

		List<Goods> allPopularGoods = goodsRepo.findAllByOrderByViewcountDesc(); // 인기순으로 정렬.

		// 중복 제거 유틸 적용
		List<GoodsDTO> popularItems = GoodsImgUtil.removeDuplicateByImgname(allPopularGoods, 10);

		return AdGoodsDTO.builder().popularItems(popularItems).build();
	}
	
	
	

	// 2. 추천 상품 (키워드 포함)
	public AdGoodsDTO getRecommendGoods(Member member) {
		
		// 1. 최근 검색어 가져오기
		List<SearchHistory> recent = searchHistoryRepo.findTop5ByMemberOrderBySearchedAtDesc(member);

		// 2. 검색어 기반 상품 수집
		List<Goods> collected = new ArrayList<>();
		for (SearchHistory sh : recent) {
			List<Goods> partial = goodsRepo.findTop20ByProductNameContainingIgnoreCase(sh.getKeyword());
			collected.addAll(partial);
		}

		// 3. 중복 제거 유틸 적용 + 최대 10개까지 자르기
		List<GoodsDTO> recommendedItems = GoodsImgUtil.removeDuplicateByImgname(collected, 10);

		return AdGoodsDTO.builder().recommendedItems(recommendedItems).build();
	}

}
