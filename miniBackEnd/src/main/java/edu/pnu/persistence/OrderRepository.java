package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.OrderList;

public interface OrderRepository extends JpaRepository<OrderList, Long>{
	List<OrderList> findByMemberUsername(String username);
}
