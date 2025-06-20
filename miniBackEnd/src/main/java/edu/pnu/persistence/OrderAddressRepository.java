package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.OrderAddress;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
	Optional <OrderAddress> findById(Long AddressId);
}
