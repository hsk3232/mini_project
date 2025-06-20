package edu.pnu.dto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderListDTO {

    // 📌 선택된 저장 주소 ID (있을 수도 있고, 없을 수도 있음)
    private Long addressId;

    // 📌 새 주소 입력이 있다면 여기에 담김
    private String zip;
    private String address1;
    private String address2;
    private String phone;

    private String payment;
    private int total;
    private String orderstatus;
}