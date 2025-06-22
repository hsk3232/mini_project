package edu.pnu.dto.Orders;

import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderList;
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
public class OrderListDTO {
    private Long orderid;
    private String username;
    private int total;
    private String orderstatus;
    private String payment;
    private OrderAddressDTO address;
    private O

    private Long addressId; // 저장된 주소일 경우만 프론트가 보내줄 수 있음 (nullable)

    public static OrderListDTO fromEntity(OrderList list) {
    	return OrderListDTO.builder()
    			.orderid(list.getOrderid())
    			.username(list.getMember().getUsername())
    			.total(list.getTotal())
    			.orderstatus(list.getOrderstatus())
    			.payment(list.getPayment())
    			.address(list.getAddress().);
    	
    	
    }
}