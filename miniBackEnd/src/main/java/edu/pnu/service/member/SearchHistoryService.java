package edu.pnu.service.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class SearchHistoryService {
	
	private SearchHistoryRepository searchHistoryRepo; 
	private GoodsRepository goodsRepo;
	
	
	public void saveSearchKeyword(String keyword, Member member) {

	    // 1. 새로운 검색어 저장
	    SearchHistory newKeyword = SearchHistory.builder()
	            .keyword(keyword)
	            .searchedAt(LocalDateTime.now())
	            .member(member)
	            .build();

	    searchHistoryRepo.save(newKeyword);

	    // 2. 최근 5개 검색어만 유지
	    List<SearchHistory> recent = searchHistoryRepo
	        .findTop5ByMemberOrderBySearchedAtDesc(member);

	    List<Long> keepIds = recent.stream()
	    	    .map(searchHistory -> searchHistory.getId())
	    	    .collect(Collectors.toList());

	    searchHistoryRepo.deleteByMemberAndIdNotIn(member, keepIds);
	}
	
	

}
