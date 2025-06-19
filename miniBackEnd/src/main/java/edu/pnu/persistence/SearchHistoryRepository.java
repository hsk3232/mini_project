package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	
	List<SearchHistory> findTop5ByMemberUsernameOrderBySearchedAtDesc(String username);

	List<SearchHistory> findTop5ByMemberOrderBySearchedAtDesc(Member member);
	
    void deleteByMemberAndSeqNotIn(Member member, List<Long> seqs);
    
    boolean existsByMemberAndKeyword(Member member, String keyword);

}
