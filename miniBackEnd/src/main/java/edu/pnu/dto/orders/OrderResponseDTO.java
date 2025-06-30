package edu.pnu.dto.orders;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
	private OrderInfoDTO orderInfo; // 주문 정보(이름, 주소, 결제 등)
	private OrderAddressDTO address; // 주소
	private List<OrderItemDTO> items; // 주문 상세(옵션별, 수량/가격 등)
}
