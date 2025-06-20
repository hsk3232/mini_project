package edu.pnu.dto.cart;

import edu.pnu.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String optionid;
    private String optionName; // 옵션명 등 필요에 따라 추가
    private int quantity;
    private int price; // 필요하면 포함

    public static CartItemDTO fromEntity(CartItem item) {
        return CartItemDTO.builder()
                .optionid(item.getGoodsOption().getOptionid())
                .optionName(item.getGoodsOption().getFullcode()) // 예시: 옵션명을 fullcode로 넣음, 상황에 맞게 수정
                .quantity(item.getQuantity())
                .price(item.getGoodsOption().getStock()) // 가격은 필요시 적절히 매핑
                .build();
    }
    
    
}
