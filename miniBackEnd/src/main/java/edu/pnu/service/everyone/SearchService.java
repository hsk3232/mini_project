package edu.pnu.service.everyone;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.domain.Member;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.service.member.SearchHistoryService;
import edu.pnu.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final GoodsRepository goodsRepo;
    private final SearchHistoryService searchHistoryService;

    // 🔍 1. 키워드 기반 검색
    public List<GoodsDTO> getfilterSearch(SearchFilterDTO dto, Member member, String sort) {
    	Sort sortObj;
    	System.out.println("🔴🔴🔴🔴🔴🔴");
    	System.out.println("[진입] : [SearchService] 키워드 기반 검색 진입 \n");
    	// 1-1. 정렬
    	switch (sort) {
        case "priceAsc" -> sortObj = Sort.by(Sort.Direction.ASC, "price");
        case "priceDesc" -> sortObj = Sort.by(Sort.Direction.DESC, "price");
        case "newest" -> sortObj = Sort.by(Sort.Direction.DESC, "registerdate");
        default -> {
            System.out.println("[경고] 잘못된 정렬 파라미터: " + sort + " → 기본값 'registerdate DESC' 사용");
            sortObj = Sort.by(Sort.Direction.DESC, "registerdate");
        	}
    	}
    	
    	// 1-2. 필터 검색 추가
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
    	
    	System.out.println(dto.getKeyword());
    	
    	// 1-3. 회원이 검색할 시 키워드 저장
    	if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
    	    spec = spec.and(CategorySpecification.hasProductName(dto.getKeyword()));
    	    if (member != null) {
    	    	searchHistoryService.saveSearchKeyword(dto.getKeyword(), member);
    	    	System.out.println("[성공] : [SearchService] 회원 검색어 정보 전달 완료");
    	    }
    	}
    	
    	// 4. JPA Specification + Sort 조합
        List<Goods> goodsList = goodsRepo.findAll(spec, sortObj);

        return goodsList.stream()
                .map(GoodsDTO::fromEntity)
                .toList();
    
    }
    
}
