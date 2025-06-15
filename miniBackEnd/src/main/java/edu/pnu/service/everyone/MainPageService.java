package edu.pnu.service.everyone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Banner;
import edu.pnu.domain.Goods;
import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.dto.MainPageDTO;
import edu.pnu.dto.MainPageGoodsDTO;
import edu.pnu.dto.category.CategoryDTO;
import edu.pnu.dto.category.CategoryMainDTO;
import edu.pnu.dto.category.CategoryMidDTO;
import edu.pnu.dto.goods.GoodsSearchDTO;
import edu.pnu.persistence.BannerRepository;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.persistence.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainPageService {
	private final GoodsRepository goodsRepo;
	private final SearchHistoryRepository searchHistoryRepo;
	private final BannerRepository bannerRepo;
	
	
    public MainPageDTO getMainPage(String keyword, Member member) {

        // 1. 배너 이미지 URL 구성
        List<Banner> banners = bannerRepo.findAllByOrderBySortOrderAsc();
        List<String> bannerImages = new ArrayList<>();
        for (Banner b : banners) {
        	bannerImages.add(b.getImagename());
            bannerImages.add("/api/public/img/banner/" + b.getImagename());
        }
        
        // 2. 카테고리 Tree
        Map<String, Map<String, Set<String>>> map = new LinkedHashMap<>();
        
        for(Goods g: goodsRepo.findAll()) {
        	map.computeIfAbsent(g.getMain(), k -> new LinkedHashMap<>())
        	.computeIfAbsent(g.getMid(), k -> new LinkedHashSet<>())
        	.add(g.getDetail());
        }
        
        List<CategoryMainDTO> categoryTree = new ArrayList<>();
        for(String main : map.keySet()) {
        	List<CategoryMidDTO> midList = new ArrayList<>();
        	for (String mid : map.get(main).keySet()) {
        		List<String> details = new ArrayList<>(map.get(main).get(mid));
        		midList.add(new CategoryMidDTO(mid, details));
        	}
        	categoryTree.add(new CategoryMainDTO(main, midList));
        }

        // 3. 인기 상품 (조회수 Top 10)
        List<Goods> popularGoods = goodsRepo.findTop10ByOrderByViewcountDesc();
        List<MainPageGoodsDTO> popularItems = new ArrayList<>();
        for (Goods g : popularGoods) {
            popularItems.add(MainPageGoodsDTO.fromEntity(g));
        }

        // 4. 추천 상품 (키워드 포함)
        List<Goods> recommendedGoods = goodsRepo.findTop10ByProductNameContainingIgnoreCase(keyword);
        List<MainPageGoodsDTO> recommendedItems = new ArrayList<>();
        for (Goods g : recommendedGoods) {
            recommendedItems.add(MainPageGoodsDTO.fromEntity(g));
        }

        // 5. 카테고리 목록 (중복 제거)
        List<Goods> allGoods = goodsRepo.findAll();
        List<CategoryDTO> categories = new ArrayList<>();
        for (Goods g : allGoods) {
            CategoryDTO dto = CategoryDTO.fromEntity(g);
            if (!categories.contains(dto)) {
                categories.add(dto);
            }
        }

        // 6. 검색 결과
        List<Goods> searchedGoods = goodsRepo.findByProductNameContainingIgnoreCase(keyword);
        List<GoodsSearchDTO> searchResults = new ArrayList<>();
        for (Goods g : searchedGoods) {
            searchResults.add(GoodsSearchDTO.fromEntity(g));
        }

        // 7. 검색어 기록 저장 (회원일 때만, 최대 5개 유지)
        if (member != null && keyword != null && !keyword.trim().isEmpty()) {
            SearchHistory history = SearchHistory.builder()
                    .member(member)
                    .keyword(keyword)
                    .searchedAt(LocalDateTime.now())
                    .build();
            searchHistoryRepo.save(history);

            List<SearchHistory> recent = searchHistoryRepo.findTop5ByMemberOrderBySearchedAtDesc(member);
            List<Long> keepIds = new ArrayList<>();
            for (SearchHistory h : recent) {
                keepIds.add(h.getId());
            }
            searchHistoryRepo.deleteByMemberAndIdNotIn(member, keepIds);
        }

        // 7. 최종 DTO 조립
        return MainPageDTO.builder()
                .bannerImg(bannerImages)
                .categoryTree(categoryTree)
                .popularItems(popularItems)
                .recommendedItems(recommendedItems)
                .categories(categories)
                .searchResults(searchResults)
                .build();
    }
    

}
