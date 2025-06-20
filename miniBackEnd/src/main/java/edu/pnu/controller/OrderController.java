package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderRequestDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.member.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

	    private final OrderService orderService;
	    private final MemberRepository memberRepo;

	    @PostMapping("/orders")
	    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO requestDTO, Principal principal) {
	        // principal에서 username 꺼내서 member 조회 (생략)
	    	System.out.println("[진입] : [OrderController] 주문하기 진입");
	    	
	        Member member = memberRepo.findByUsername(principal.getName()).orElse(null);
	        OrderList order = orderService.createOrder(member, requestDTO.getItems(), requestDTO.getOrderInfo());
	        
	        System.out.println("[성공] : [OrderController] 주문 완료");
	        return ResponseEntity.ok(order.getOrderid());
	    }
	

}
