package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {

}
