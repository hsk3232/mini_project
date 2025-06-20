package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Cart;
import edu.pnu.domain.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {
	 Optional<Cart> findByMember(Member member);
}
