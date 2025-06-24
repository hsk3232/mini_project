package edu.pnu.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;


public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
	Optional <OrderAddress> findById(Long addressId);
	
	Optional <OrderAddress> findByMember(Member member);
	Optional <OrderAddress> findByMember_Username(String username);
	
	List<OrderAddress> findAllByMember(Member member);
	List<OrderAddress> findAllByMemberAndDeleteAddrFalse(Member member);

}
