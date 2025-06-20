package edu.pnu.service.member;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Cart;
import edu.pnu.domain.CartItem;
import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.dto.cart.CartDTO;
import edu.pnu.dto.cart.CartItemDTO;
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
	public CartDTO getCart(Cart cart) {
		List<CartItemDTO> itemDTOs = cart.getCartItems().stream()
				.map((CartItem item) -> CartItemDTO.builder()
					.optionid(item.getGoodsOption().getOptionid())
		            .imgname(item.getGoodsOption().getImgname())
		            .productName(item.getGoodsOption().getGoods().getProductName())
		            .price(item.getGoodsOption().getGoods().getPrice()) // ✅ 여기!
		            .quantity(item.getQuantity())
		            .size(item.getGoodsOption().getSize())
		            .imgUrl(item.getGoodsOption().getGoods().getImgAdressList().stream()
		                    .filter(img -> img.isIsmain()) // ismain이 true인 것만
		                    .filter(img -> img.getImgname()
		                    					.equals(item.getGoodsOption()
		                    							.getImgname())) // imgname이 같은 것만
		                    .findFirst()
		                    .map(img -> img.getImgUrl()) // imgUrl만 추출
		                    .orElse(null)) // 없으면 null
		            .build())
				.collect(Collectors.toList());

		return CartDTO.builder()
				.username(cart.getMember().getUsername())
				.items(itemDTOs)
				.build();
	}
	
	
	 @Transactional
	    public void deleteItemFromCart(String optionid, String username) {
	       
	        Cart cart = cartRepo.findByMember_Username(username)
	            .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

	        CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(optionid, cart)
	            .orElseThrow(() -> new IllegalArgumentException("해당 상품이 장바구니에 없음"));

	        cartItemRepo.delete(item);
	    }
	 
	 @Transactional
	 public void deleteClearCart(String username) {
	
	     Cart cart = cartRepo.findByMember_Username(username)
	         .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

	     List<CartItem> items = cartItemRepo.findByCart(cart);
	     cartItemRepo.deleteAll(items);
	 }
	
}
