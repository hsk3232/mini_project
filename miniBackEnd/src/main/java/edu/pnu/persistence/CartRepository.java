package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByMember_Username(String username);


}
