package edu.pnu.dto.Orders;

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
public class OrderItemDTO {
    private String optionid;    // 어떤 옵션인지 (GoodsOption PK)
    private int quantity;       // 몇 개 샀는지
    private int price;          // 개당 가격 또는 총액 (실무에 따라)
}