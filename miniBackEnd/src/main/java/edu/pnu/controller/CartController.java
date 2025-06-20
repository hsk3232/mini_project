package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Cart;
import edu.pnu.domain.Member;
import edu.pnu.dto.cart.AddToCartRequestDTO;
import edu.pnu.dto.cart.CartDTO;
import edu.pnu.persistence.CartRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.member.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepo;
    private final CartRepository cartRepo;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequestDTO dto, Principal principal) {
        System.out.println("[진입] : [CartController] 장바구니 담기 진입");
        String username = principal.getName();
        System.out.println(dto.toString());
    	cartService.addToCart(username, dto.getItems());
        System.out.println("[성공] : [CartController] 장바구니 담기 완료");
        return ResponseEntity.ok().build();
    }
    @GetMapping("/cart/list")
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal Member member) {
    	
    	Cart cart = cartRepo.findByMember_Username(member.getUsername())
    		    .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
        CartDTO dto = cartService.convertToCartDTO(cart);
        return ResponseEntity.ok(dto);
    }
    
    // 선택 삭제
    @DeleteMapping("/cart/remove/{optionid}")
	public ResponseEntity<?> deleteCartItem(@PathVariable String optionid, Principal principal) {
	    String username = principal.getName();
	    cartService.deleteItemFromCart(username, optionid);
	    return ResponseEntity.ok().build();
	}
    
    @DeleteMapping("/cart/remove")
    public ResponseEntity<?> deleteAllCartItems(Principal principal) {
        String username = principal.getName();
        cartService.clearCart(username);
        return ResponseEntity.ok().build();
    }

}
