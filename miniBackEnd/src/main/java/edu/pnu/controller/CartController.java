package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.cart.CartAddRequestDTO;
import edu.pnu.dto.cart.CartDTO;
import edu.pnu.dto.cart.CartRemainListDTO;
import edu.pnu.dto.cart.CartUpdateRequestDTO;
import edu.pnu.service.member.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartAddRequestDTO dto, Principal principal) {
        System.out.println("[진입] : [CartController] 장바구니 담기 진입");
        
        String username = principal.getName();
        
        System.out.println("[실행] : 담긴 상품 " + dto.getItems().toString());
    	
        cartService.addToCart(dto.getItems(), username);
        
    	System.out.println("[성공] : [CartController] 장바구니 담기 완료 \n");
        return ResponseEntity.ok().build();
    }
    
    
    
    @GetMapping("/cart/list")
    public ResponseEntity<?> getCart(Principal principal) {
    	System.out.println("[진입] : [CartController] 카트 리스트 진입");
    	
    	String username = principal.getName();
        CartDTO dto = cartService.getCart(username);
        
        System.out.println("[성공] : [CartController] 카트 리스트 로딩 완료 \n");
        return ResponseEntity.ok(dto);
    }
    
    
    
    @PatchMapping("/cart/update")
    public ResponseEntity<?> updateCart(Principal principal,
        @RequestBody CartUpdateRequestDTO request
    ) {
        System.out.println("[진입] : [CartController] 수량 변경 요청");
        String username = principal.getName();
        if (request.getOptionid() == null || request.getQuantityChange() == null) {
            return ResponseEntity.badRequest().body("optionid와 quantityChange는 필수입니다.");
        }

        CartDTO updatedCart = cartService.updateCart(
        		username,
            request.getOptionid(),
            request.getQuantityChange()
        );

        System.out.println("[성공] : [CartController] 수량 변경 완료");
        return ResponseEntity.ok(updatedCart);
    }
    
    
 // 🛒 주문한 상품을 장바구니에서 숨김 처리 (remain = false)
    @PatchMapping("/cart/remain")
    public ResponseEntity<?> removeCartItemsLogically(@RequestBody CartRemainListDTO items, Principal principal) {
        String username = principal.getName();

        System.out.println("[진입] : [CartController] remain 상태 변경 요청 - 사용자: " + username);

        cartService.updateRemainStatus(items, username);

        System.out.println("[성공] : [CartController] remain 상태 false 처리 완료\n");
        return ResponseEntity.ok().build();
    }
    
    
    // 선택 삭제
    @DeleteMapping("/cart/remove/{optionid}")
	public ResponseEntity<?> deleteCartItem(@PathVariable String optionid, Principal principal) {
    	System.out.println(optionid);
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
