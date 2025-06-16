package edu.pnu.service.everyone;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.dto.search.SearchResultsDTO;
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
    public SearchResultsDTO getKeywordSearchResults(String keyword, Member member) {
    	 if (member != null && keyword != null && !keyword.isBlank()) {
             saveSearchKeyword(keyword, member);
         }

         List<GoodsDTO> results = goodsRepo.findByProductNameContainingIgnoreCase(keyword)
                                           .stream().map(GoodsDTO::from).toList();

         return SearchResultsDTO.builder()
                 .searchResults(results)
                 .build();
     }

     // 2. 필터 검색
    public List<GoodsDTO> getFilteredSearchResults(SearchFilterDTO dto, Member member) {
        Specification<Goods> spec = CategorySpecification.filterBy(
                dto.getMain(),
                dto.getMid(),
                dto.getDetail(),
                dto.getGender(),
                dto.getColor(),
                dto.getPrint(),
                dto.getMinPrice(),
                dto.getMaxPrice()
        );

        return goodsRepo.findAll(spec)
                .stream()
                .map(GoodsDTO::from)
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
    
    // 1-1. 메서드 오버로딩 -> CategoryService에서 쓰는 것.
    public List<GoodsDTO> getFilteredSearchResults(SearchFilterDTO dto) {
	    return getFilteredSearchResults(dto, null); // null 전달
	}
}
