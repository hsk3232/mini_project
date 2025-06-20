package edu.pnu.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Cart;
import edu.pnu.domain.CartItem;
import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.dto.cart.CartDTO;
import edu.pnu.dto.cart.CartItemDTO;
import edu.pnu.dto.cart.CartRemainDTO;
import edu.pnu.dto.cart.CartRemainListDTO;
import edu.pnu.persistence.CartItemRepository;
import edu.pnu.persistence.CartRepository;
import edu.pnu.persistence.GoodsOptionRepository;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepo;
	private final GoodsOptionRepository goodsOptionRepo;
	private final MemberRepository memberRepo;
	private final CartItemRepository cartItemRepo;

	// 1. 장바구니 DB 저장 메서드
	@Transactional
	public void addToCart(List<CartItemDTO> items, String username) {

		Member member = memberRepo.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("회원 없음: " + username));

		// 회원의 장바구니가 없다면 생성.
		Cart cart = cartRepo.findByMember_Username(username).orElseGet(() -> cartRepo.save(new Cart(member)));

		// 장바구니에 담을 상품 옵션을 조회하고 CartList에 저장
		for (CartItemDTO d : items) {
			GoodsOption option = goodsOptionRepo.findById(d.getOptionid())
					.orElseThrow(() -> new IllegalArgumentException("옵션 없음: " + d.getOptionid()));

			CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(d.getOptionid(), cart).orElse(null);

			if (item != null) {
				item.setQuantity(item.getQuantity() + d.getQuantity());
			} else {
				CartItem newItem = new CartItem(option, cart, d.getQuantity());
				cartItemRepo.save(newItem);
			}
		}
	}

	// 2. 장바구니 front 전달 메서드
	public CartDTO getCart(String username) {
		Cart cart = cartRepo.findByMember_Username(username).orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));
		return CartDTO.fromEntity(cart); // 또는 그대로 변환 로직 유지
	}

	// 3. 장바구니 업데이트 메서드
	@Transactional
	public CartDTO updateCart(String username, String optionId, int quantityChange) {
		Cart cart = cartRepo.findByMember_Username(username).orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

		CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(optionId, cart)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품 없음"));

		int newQty = item.getQuantity() + quantityChange;

		if (newQty <= 0) {
			cartItemRepo.delete(item);
		} else {
			item.setQuantity(newQty);
		}

		return CartDTO.fromEntity(cart);
	}

	@Transactional
	public void markItemsAsRemoved(CartRemainListDTO items, String username) {
	    Cart cart = cartRepo.findByMember_Username(username)
	        .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

	    for (CartRemainDTO dto : items.getItems()) {
	        CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(dto.getOptionid(), cart)
	            .orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음: " + dto.getOptionid()));

	        if (item.isRemain()) {
	            item.setRemain(false);
	            cartItemRepo.save(item);
	        }
	    }
	}

	// 4. 장바구니에서 특정 상품 제거
	@Transactional
	public void deleteItemFromCart(String optionid, String username) {

		Cart cart = cartRepo.findByMember_Username(username).orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

		CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(optionid, cart)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 장바구니에 없음"));

		cartItemRepo.delete(item);
	}

	// 4. 장바구니 전체 비우기
	@Transactional
	public void deleteClearCart(String username) {

		Cart cart = cartRepo.findByMember_Username(username).orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

		List<CartItem> items = cartItemRepo.findByCart(cart);
		cartItemRepo.deleteAll(items);
	}

}
