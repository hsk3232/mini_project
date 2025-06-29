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

    // 🔹 공통: 카트 조회 + 없으면 예외
    private Cart findCartOrThrow(String username) {
        return cartRepo.findByMember_Username(username)
                .orElseThrow(() -> new IllegalArgumentException("카트가 존재하지 않습니다: " + username));
    }

    // 1️⃣ 장바구니 담기 (없으면 카트 생성)
    @Transactional
    public void addToCart(List<CartItemDTO> items, String username) {
        Member member = memberRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음: " + username));

        Cart cart = cartRepo.findByMember_Username(username)
                .orElseGet(() -> {
                    Cart newCart = new Cart(member);
                    return cartRepo.save(newCart);
                });

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

    // 2️⃣ 장바구니 조회 (없으면 빈 DTO 반환)
    public CartDTO getCart(String username) {
        return cartRepo.findByMember_Username(username)
                .map(this::buildCartDTO)
                .orElseGet(CartDTO::new); // 카트 없으면 빈 DTO
    }

    // 3️⃣ 장바구니 수량 변경
    @Transactional
    public CartDTO updateCart(String username, String optionId, int quantityChange) {
        Cart cart = findCartOrThrow(username);

        CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(optionId, cart)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 없음: " + optionId));

        int newQty = item.getQuantity() + quantityChange;
        if (newQty <= 0) {
            cartItemRepo.delete(item);
        } else {
            item.setQuantity(newQty);
        }

        return buildCartDTO(cart);
    }

    // 4️⃣ 장바구니 remain 상태 변경
    @Transactional
    public void updateRemainStatus(CartRemainListDTO items, String username) {
        Cart cart = findCartOrThrow(username);

        for (CartRemainDTO dto : items.getItems()) {
            CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(dto.getOptionid(), cart)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품 없음: " + dto.getOptionid()));
            item.setRemain(!item.isRemain());
            cartItemRepo.save(item);
        }
    }

    // 5️⃣ 장바구니 특정 상품 삭제
    @Transactional
    public void deleteItemFromCart(String optionid, String username) {
        Cart cart = findCartOrThrow(username);

        CartItem item = cartItemRepo.findByGoodsOption_OptionidAndCart(optionid, cart)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 없음: " + optionid));

        cartItemRepo.delete(item);
    }

    // 6️⃣ 장바구니 전체 비우기
    @Transactional
    public void deleteClearCart(String username) {
        Cart cart = findCartOrThrow(username);
        List<CartItem> items = cartItemRepo.findByCart(cart);
        cartItemRepo.deleteAll(items);
    }

    // 🔹 공통 DTO 생성 메서드
    private CartDTO buildCartDTO(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getCartItems().stream()
                .filter(CartItem::isRemain)
                .map(CartItemDTO::fromEntity)
                .collect(Collectors.toList());

        CartDTO dto = new CartDTO();
        dto.setItems(itemDTOs);
        return dto;
    }
}
