package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	
    @EntityGraph(attributePaths = {
            "items.goodsOption.goods.imgAdressList",   // 주문 → 아이템 → 옵션 → 상품
            "address"                    // 주문 → 주소
        })
    List<OrderList> findByMember_Username(String username);
}
