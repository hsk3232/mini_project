package edu.pnu.service.everyone;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.persistence.SearchHistoryRepository;
import edu.pnu.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final GoodsRepository goodsRepo;
    private final SearchHistoryRepository searchHistoryRepo;

    // 🔍 1. 키워드 기반 검색
    public List<GoodsDTO> getFilteredSearchResults(SearchFilterDTO dto, Member member, String orderBy) {
    	Sort sort;
    	
    	switch (orderBy) {
        case "priceAsc" -> sort = Sort.by(Sort.Direction.ASC, "price");
        case "priceDesc" -> sort = Sort.by(Sort.Direction.DESC, "price");
        case "newest" -> sort = Sort.by(Sort.Direction.DESC, "registerdate");
        default -> {
            System.out.println("[경고] 잘못된 정렬 파라미터: " + orderBy + " → 기본값 'registerdate DESC' 사용");
            sort = Sort.by(Sort.Direction.DESC, "registerdate");
        	}
    	}
    	
    	// 2. 필터 검색 추가
    	Specification<Goods> spec = CategorySpecification.filterBy(
    			dto.getMain(),
                dto.getMid(),
                dto.getDetail(),
                dto.getGender(),
                dto.getColor(),
                dto.getPrint(),
                dto.getMinPrice(),
                dto.getMaxPrice(),
                dto.getKeyword()
        );
    	

    	// 3. 회원이 검색할 시 키워드 저장
    	if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
    	    spec = spec.and(CategorySpecification.hasProductName(dto.getKeyword()));
    	    if (member != null) saveSearchKeyword(dto.getKeyword(), member);
    	}
    	
    	// 4. JPA Specification + Sort 조합
        List<Goods> goodsList = goodsRepo.findAll(spec, sort);

        return goodsList.stream()
                .map(GoodsDTO::fromEntity)
                .toList();
    
    }
    

    // 3. 검색어 저장 (최근 5개만 유지)
    private void saveSearchKeyword(String keyword, Member member) {
        SearchHistory entity = SearchHistory.builder()
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .member(member)
                .build();

        searchHistoryRepo.save(entity);

        List<SearchHistory> recent = searchHistoryRepo.findTop5ByMemberOrderBySearchedAtDesc(member);
        List<Long> keepIds = recent.stream().map(SearchHistory::getId).toList();
        searchHistoryRepo.deleteByMemberAndIdNotIn(member, keepIds);
    }    
}
