package edu.pnu.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderItemDTO;
import edu.pnu.dto.Orders.OrderListDTO;
import edu.pnu.persistence.GoodsOptionRepository;
import edu.pnu.persistence.OrderListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderListRepository orderListRepo;
    private final GoodsOptionRepository goodsOptionRepo;

    @Transactional
    public OrderList createOrder(Member member, List<OrderItemDTO> itemDTOs, OrderListDTO orderListDTO) {
        // 1. 주문 생성
        OrderList order = OrderList.builder()
            .member(member)
            .orderstatus("주문완료")
            .total(orderListDTO.getTotal())
            .name(orderListDTO.getName())
            .zip(orderListDTO.getZip())
            .address1(orderListDTO.getAddress1())
            .address2(orderListDTO.getAddress2())
            .phone(orderListDTO.getPhone())
            .payment(orderListDTO.getPayment())
            .build();

        // 2. 주문상세 추가 + 옵션 재고 차감
        for (OrderItemDTO itemDTO : itemDTOs) {
            GoodsOption option = goodsOptionRepo.findById(itemDTO.getOptionid())
                .orElseThrow(() -> new IllegalArgumentException("옵션 없음!"));

            if (option.getStock() < itemDTO.getQuantity())
                throw new IllegalArgumentException("재고 부족!");

            option.setStock(option.getStock() - itemDTO.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                .orderList(order)
                .goodsOption(option)
                .quantity(itemDTO.getQuantity())
                .price(itemDTO.getPrice())
                .build();

            order.getItems().add(orderItem);
        }
        orderListRepo.save(order);
        return order;
    }
}
