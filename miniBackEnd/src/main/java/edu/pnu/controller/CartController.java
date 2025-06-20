package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.cart.CartItemDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.member.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDTO dto, Principal principal) {
        Member member = memberRepo.findByUsername(principal.getName()).orElse(null);
        cartService.addToCart(member, dto.getOptionid(), dto.getQuantity());
        System.out.println("[성공] : [CartController] 장바구니 담기 완료");
        return ResponseEntity.ok().build();
    }
}
