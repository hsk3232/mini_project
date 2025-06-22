package edu.pnu.dto.Orders;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private OrderListDTO orderInfo;        // 주문 정보(이름, 주소, 결제 등)
    private List<OrderItemDTO> items;      // 주문 상세(옵션별, 수량/가격 등)
}