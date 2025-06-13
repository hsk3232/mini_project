package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;
import edu.pnu.domain.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	
    List<SearchHistory> findTop5ByMemberOrderBySearchedAtDesc(Member member);

    void deleteByMemberAndIdNotIn(Member member, List<Long> keepIds);
	
}
