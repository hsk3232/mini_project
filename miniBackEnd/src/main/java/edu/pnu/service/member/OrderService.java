package edu.pnu.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.Orders.OrderItemDTO;
import edu.pnu.dto.Orders.OrderListDTO;
import edu.pnu.dto.Orders.OrderRequestDTO;
import edu.pnu.persistence.GoodsOptionRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.OrderAddressRepository;
import edu.pnu.persistence.OrderListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderListRepository orderListRepo;
    private final GoodsOptionRepository goodsOptionRepo;
    private final OrderAddressRepository orderAddressRepo;
    private final MemberRepository memberRepo;
    
    	@Transactional
    	public OrderList createOrder(Member member, OrderRequestDTO requestDTO) {
    	    System.out.println("[진입] : [OrderService] 주문 내역 생성");

    	    // 1. DTO 분해
    	    OrderListDTO dto = requestDTO.getOrderInfo();
    	    List<OrderItemDTO> itemDTOs = requestDTO.getItems();

    	    // 2. 주소 처리
    	    OrderAddress address;
    	    if (dto.getAddressId() != null) {
    	        address = orderAddressRepo.findById(dto.getAddressId())
    	            .orElseThrow(() -> new IllegalArgumentException("주소 없음"));
    	    } else {
    	        address = OrderAddress.builder()
    	        	.member(member)
    	        	.name(dto.getAddress().getName())              // 🔥 주문자 이름 추가
    	            .zip(dto.getAddress().getZip())
    	            .address1(dto.getAddress().getAddress1())
    	            .address2(dto.getAddress().getAddress2())
    	            .phone(dto.getAddress().getPhone())
    	            .build();
    	        orderAddressRepo.save(address);
    	    }

    	    // 3. 주문 생성
    	    OrderList order = OrderList.builder()
    	        .member(member)
    	        .orderstatus(dto.getOrderstatus() != null ? dto.getOrderstatus() : "주문완료")
    	        .total(dto.getTotal())
    	        .payment(dto.getPayment())
    	        .address(address)
    	        .build();

    	    // 4. 주문 아이템 처리 + 재고 차감
    	    for (OrderItemDTO itemDTO : itemDTOs) {
    	        GoodsOption option = goodsOptionRepo.findById(itemDTO.getOptionid())
    	            .orElseThrow(() -> new IllegalArgumentException("옵션 없음: " + itemDTO.getOptionid()));

    	        if (option.getStock() < itemDTO.getQuantity()) {
    	            throw new IllegalArgumentException("재고 부족: " + itemDTO.getOptionid());
    	        }

    	        option.setStock(option.getStock() - itemDTO.getQuantity());

    	        OrderItem orderItem = OrderItem.builder()
    	            .orderList(order)
    	            .goodsOption(option)
    	            .quantity(itemDTO.getQuantity())
    	            .price(itemDTO.getPrice())
    	            .build();

    	        order.getItems().add(orderItem);
    	    }

    	    // 5. 저장
    	    orderListRepo.save(order);
    	    System.out.println("[성공] : 주문 저장 완료");
    	    return order;
    	}
    	
    	@Transactional
    	public OrderList createOrderByUsername(String username, OrderRequestDTO dto) {
    	    Member member = memberRepo.findByUsername(username)
    	        .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
    	    
    	    return createOrder(member, dto);
    	}
}
