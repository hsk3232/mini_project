package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Cart;
import edu.pnu.dto.cart.AddToCartRequestDTO;
import edu.pnu.dto.cart.CartDTO;
import edu.pnu.persistence.CartRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.member.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
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
        log.info("[진입] : [CartController] 카트 리스트 진입");
        String username = principal.getName();
        System.out.println(dto.toString());
    	cartService.addToCart(dto.getItems(), username);
        System.out.println("[성공] : [CartController] 장바구니 담기 완료 \n");
        return ResponseEntity.ok().build();
    }
    
    
    
    @GetMapping("/cart/list")
    public ResponseEntity<?> getCart(Principal principal) {
    	System.out.println("[진입] : [CartController] 카트 리스트 진입");
    	String member = principal.getName();
    	Cart cart = cartRepo.findByMember_Username(member)
    		    .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
    	
        CartDTO dto = cartService.getCart(cart);
        System.out.println("[성공] : [CartController] 카트 리스트 로딩 완료 \n");
        return ResponseEntity.ok(dto);
    }
    
    // 선택 삭제
    @DeleteMapping("/cart/remove/{optionid}")
	public ResponseEntity<?> deleteCartItem(@PathVariable String optionid, Principal principal) {
    	
    	System.out.println("[진입] : [CartController] 선택 삭제 진입");
	    
    	String username = principal.getName();
	    cartService.deleteItemFromCart(optionid, username);
	    
	    System.out.println("[성공] : [CartController] 선택 삭제 성공 \n");
	    return ResponseEntity.ok().build();
	}
    
    
    
    @DeleteMapping("/cart/remove")
    public ResponseEntity<?> deleteClearCart(Principal principal) {
    	System.out.println("[진입] : [CartController] 전체 삭제 진입");
        
    	String username = principal.getName();
        cartService.deleteClearCart(username);
        
        System.out.println("[성공] : [CartController] 전체 삭제 성공 \n");
        return ResponseEntity.ok().build();
    }

}
