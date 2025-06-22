package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByMember_Username(String username);

}
