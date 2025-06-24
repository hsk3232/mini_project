package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	// 3. 주문하기 모듈
	@PostMapping("/orders")
	public ResponseEntity<?> createOrder(Principal principal, @RequestBody OrderRequestDTO requestDTO) {
		System.out.println("[진입] : [OrderController] 주문하기 진입");
		String username = principal.getName();
		OrderList order = orderService.createOrder(username, requestDTO);
		System.out.println("[성공] : [OrderController] 주문하기 성공");
		return ResponseEntity.ok(order.getOrderid());
	}

	// ✅ 내 주문 내역 조회 (OrderRequestDTO로 응답)
	@GetMapping("/orderlist")
	public ResponseEntity<?> getMyOrders(Principal principal) {
		try {
			System.out.println("[진입] : [OrderController] 내역조회 진입");
			String username = principal.getName();
			List<OrderRequestDTO> orders = orderService.getMyOrders(username);
			System.out.println("[성공] : [OrderController] 내역조회 성공");
			return ResponseEntity.ok(orders);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("[오류] 주문 내역 조회 실패");
		}
	}

}
