package edu.pnu.service.member;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.persistence.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
	
	private final SearchHistoryRepository searchHistoryRepo; 
	
	
	public void saveSearchKeyword(String keyword, Member member) {

	    // 1. 새로운 검색어 저장
	    SearchHistory newKeyword = SearchHistory.builder()
	            .keyword(keyword)
	            .searchedAt(LocalDateTime.now())
	            .member(member)
	            .build();

	    searchHistoryRepo.save(newKeyword);
	    System.out.println("[회원 검색어 저장 완료]");

	    // 2. 최근 5개 검색어만 유지
	    List<SearchHistory> recent = searchHistoryRepo
	        .findTop5ByMemberOrderBySearchedAtDesc(member);

	    List<Long> seqs = recent.stream().map(SearchHistory::getSeq).toList();
        searchHistoryRepo.deleteByMemberAndSeqNotIn(member, seqs);
        System.out.println("[비회원 검색어 삭제 완료]");
	}
	
	

}
