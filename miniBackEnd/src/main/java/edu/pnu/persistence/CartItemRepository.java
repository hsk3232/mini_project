package edu.pnu.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Cart;
import edu.pnu.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByGoodsOption_OptionidAndCart(String optionid, Cart cart);
	
	List<CartItem> findByCart(Cart cart);
}
