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
	private String productName;		// 상품명
    private int price;
    private int quantity;
    private String size;
    private String imgUrl;
    private boolean ismain;
    

    public static CartItemDTO fromEntity(CartItem item) {
        return CartItemDTO.builder()
            .optionid(item.getGoodsOption().getOptionid())
            .imgname(item.getGoodsOption().getImgname())
            .productName(item.getGoodsOption().getGoods().getProductName())
            .price(item.getGoodsOption().getGoods().getPrice()) // ✅ 여기!
            .quantity(item.getQuantity())
            .size(item.getGoodsOption().getSize())
            .imgUrl(item.getGoodsOption().getGoods().getImgAdressList().get(0))
            .build();
    }
}
