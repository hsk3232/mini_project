package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	
	// 원래 데이터를 그대로 사용할 때
    List<OrderList> findByMember_Username(String username);

    
    // 성능 개선 버전: fetch join으로 연관된 데이터를 한 방에 로드
    @Query("SELECT o FROM OrderList o " +
           "LEFT JOIN FETCH o.address " +
           "LEFT JOIN FETCH o.member " +
           "WHERE o.member.username = :username")
    List<OrderList> findAllWithMemberAndAddressByUsername(String username);
    
}
