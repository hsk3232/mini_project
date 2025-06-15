package edu.pnu.service.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.persistence.SearchHistoryRepository;

@Service
public class SearchHistoryService {
	
	private SearchHistoryRepository searchHistoryRepo; 
	
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
