package edu.pnu.dto.orders;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewAddRequestDTO {
    private Long orderid;     // 주문번호
    private String optionid;  // 구매 옵션 ID
    private String reviewtext;
    private int rating;
}