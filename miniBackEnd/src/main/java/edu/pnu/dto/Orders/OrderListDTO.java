package edu.pnu.dto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderListDTO {
    private String name;
    private String zip;
    private String address1;
    private String address2;
    private String phone;
    private String payment;
    private int total;
    private String orderstatus;  // "주문완료" 등
}
