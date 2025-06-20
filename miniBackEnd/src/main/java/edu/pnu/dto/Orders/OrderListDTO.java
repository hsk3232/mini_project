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
    private String orderstatus;

    private Long addressId; // 저장된 주소일 경우만 프론트가 보내줄 수 있음 (nullable)
}