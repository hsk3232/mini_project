package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ReviewList;
import edu.pnu.dto.Orders.ReviewListDTO;

public interface ReviewRepository extends JpaRepository<ReviewList, Long> {
	List<ReviewList> findAllByMember_Username(String username);
	ReviewList existsByMember_UsernameAndOrderList_Orderid(String username, Long orderid);
	boolean existsByMember_UsernameAndOrderList_OrderidAndGoodsOption_Optionid(String username, Long orderid, String optionid);
}
