package edu.pnu.service.member;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Cart;
import edu.pnu.domain.CartItem;
import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.persistence.CartRepository;
import edu.pnu.persistence.GoodsOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final GoodsOptionRepository goodsOptionRepo;

    @Transactional
    public void addToCart(Member member, String optionid, int quantity) {
        Cart cart = cartRepo.findByMember(member).orElseGet(() -> cartRepo.save(new Cart(member)));
        GoodsOption option = goodsOptionRepo.findById(optionid)
            .orElseThrow(() -> new IllegalArgumentException("옵션 없음!"));
        // 이미 담긴 상품이면 수량만 증가
        CartItem item = cart.findItem(optionid);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(cart, option, quantity));
        }
        cartRepo.save(cart);
    }
}

