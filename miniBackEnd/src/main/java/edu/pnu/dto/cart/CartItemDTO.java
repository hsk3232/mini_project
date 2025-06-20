package edu.pnu.dto.cart;

import edu.pnu.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemDTO {
	private String optionid;
	private String imgname;
    private int price;
    private int quantity;
    private String size;

    public static CartItemDTO fromEntity(CartItem item) {
        return CartItemDTO.builder()
            .optionid(item.getGoodsOption().getOptionid())
            .imgname(item.getGoodsOption().getImgname())
            .price(item.getGoodsOption().getGoods().getPrice()) // ✅ 여기!
            .quantity(item.getQuantity())
            .size(item.getGoodsOption().getSize())
            .build();
    }
}
