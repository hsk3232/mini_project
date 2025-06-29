package edu.pnu.dto.Orders;

import java.time.LocalDateTime;

import edu.pnu.domain.Member;
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
public class OrderInfoDTO {
    private Long orderid;
    private String username;
    private int total;
    private String orderstatus;
    private String payment;
    private LocalDateTime orderdate;


    private Long addressId; // 저장된 주소일 경우만 프론트가 보내줄 수 있음 (nullable)

    
    // Entity → DTO 데이터 변환, 즉 Entity의 DB를 DTO로 불러옴
    public static OrderInfoDTO fromEntity(OrderList list) {
    	return OrderInfoDTO.builder()
    			.orderid(list.getOrderid())
    			.username(list.getMember().getUsername())
    			.total(list.getTotal())
    			.orderstatus(list.getOrderstatus())
    			.payment(list.getPayment())
    			.orderdate(list.getOrderdate())
    			.build();   	
    }
    
 // DTO → Entity 데이터 변환, 즉 DTO의 데이터를 저장함. 
    public static OrderList toEntity(Member member, OrderInfoDTO o,  OrderAddress address) {
    	return OrderList.builder()
    			.member(member)
    			.total(o.getTotal())
    			.orderstatus(o.getOrderstatus() != null ? o.getOrderstatus() : "주문완료")
    			.payment(o.getPayment())
    			.orderdate(o.getOrderdate())
    			.address(address)
    			.build();
    }
    
    
}