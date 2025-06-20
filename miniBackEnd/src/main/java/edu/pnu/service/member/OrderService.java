package edu.pnu.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderItemDTO;
import edu.pnu.dto.Orders.OrderListDTO;
import edu.pnu.persistence.GoodsOptionRepository;
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

    @Transactional
    public OrderList createOrder(Member member, List<OrderItemDTO> itemDTOs, OrderListDTO orderListDTO) {
       
    	System.out.println("[진입] : [OrderService] 주문 내역 생성");
    	
    	 // 1. 배송지 엔티티 조회 (추가해야 작동됨)
    	OrderAddress address;

    	if (orderListDTO.getAddressId() != null) {
    	    // 🔹 저장된 주소 사용
    	    address = orderAddressRepo.findById(orderListDTO.getAddressId())
    	        .orElseThrow(() -> new IllegalArgumentException("주소 없음"));
    	} else {
    	    // 새 주소 입력시 사용
    	    address = OrderAddress.builder()
    	        .zip(orderListDTO.getZip())
    	        .address1(orderListDTO.getAddress1())
    	        .address2(orderListDTO.getAddress2())
    	        .phone(orderListDTO.getPhone())
    	        .member(member)
    	        .build();
    	    
    	    orderAddressRepo.save(address); // 새 주소 저장
    	}
    	
        
    	 // 2. 주문 생성
        OrderList order = OrderList.builder()
            .member(member)
            .orderstatus(orderListDTO.getOrderstatus() != null ? orderListDTO.getOrderstatus() : "주문완료")
            .total(orderListDTO.getTotal())
            .payment(orderListDTO.getPayment())
            .address(address)
            .build();

        
        
        // 3. 주문 상세 항목 처리 + 재고 차감
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

        // 4. 최종 주문 저장
        orderListRepo.save(order);
        System.out.println("[성공] : 주문 저장 완료");
        return order;
    }
}
