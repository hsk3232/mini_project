package edu.pnu.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ReviewList;

public interface ReviewRepository extends JpaRepository<ReviewList, Long> {
	List<ReviewList> findAllByMember_Username(String username);
	ReviewList existsByMember_UsernameAndOrderList_Orderid(String username, Long orderid);
	boolean existsByMember_UsernameAndOrderList_OrderidAndGoodsOption_Optionid(String username, Long orderid, String optionid);
	
	Optional<ReviewList> findByMember_UsernameAndOrderList_Orderid(String username, Long orderid);
	
	List<ReviewList> findAllByGoods_ImgnameAndRemainTrue(String imgname);

}
