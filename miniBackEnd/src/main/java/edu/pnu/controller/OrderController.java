package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderRequestDTO;
import edu.pnu.service.member.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class OrderController {

	    private final OrderService orderService;
	    

	    @PostMapping("/orders")
	    public ResponseEntity<?> createOrder(
	        Principal principal,
	        @RequestBody OrderRequestDTO requestDTO
	    ) {
	        String username = principal.getName();
	        OrderList order = orderService.createOrderByUsername(username, requestDTO);

	        return ResponseEntity.ok(order.getOrderid());
	    }
	

}
