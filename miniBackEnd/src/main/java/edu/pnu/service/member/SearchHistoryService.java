package edu.pnu.service.member;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;
import edu.pnu.persistence.SearchHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
	
	private final SearchHistoryRepository searchHistoryRepo; 
	
	@Transactional
	public void saveSearchKeyword(String keyword, Member member) {
		
		// 1. 비회원일 경우, 저장하지 않음
        if (member == null) {
            System.out.println("[경고] : [SearchHistoryService] 비회원은 검색어를 저장 불가");
            return;
        }

        // 2. 이미 저장된 키워드가 있는지 확인
        boolean exists = searchHistoryRepo.existsByMemberAndKeyword(member, keyword);
        if (exists) {
            System.out.println("[경고] : [SearchHistoryService] 이미 저장된 검색어라 저장 하지 않음");
            return;  // 이미 존재하는 경우 저장하지 않음
        }
		
	    // 1. 새로운 검색어 저장
	    SearchHistory newKeyword = SearchHistory.builder()
	            .keyword(keyword)
	            .searchedAt(LocalDateTime.now())
	            .member(member)
	            .build();

	    searchHistoryRepo.save(newKeyword);
	    System.out.println("[성공] : [SearchHistoryService] 회원 검색어 저장 완료");

	    // 2. 최근 5개 검색어만 유지
	    List<SearchHistory> recent = searchHistoryRepo
	        .findTop5ByMemberOrderBySearchedAtDesc(member);

	    List<Long> seqs = recent.stream().map(SearchHistory::getSeq).toList();
        searchHistoryRepo.deleteByMemberAndSeqNotIn(member, seqs);
        System.out.println("[성공] : [SearchHistoryService] 오래된 검색어 삭제 완료 \n");
	}
	
	

}
