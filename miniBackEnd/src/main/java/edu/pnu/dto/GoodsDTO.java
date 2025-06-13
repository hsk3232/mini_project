package edu.pnu.dto;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {
	private String productName;
    private int price;
    private String color;

    public static GoodsDTO from(Goods goods) {
        return new GoodsDTO(
            goods.getProductName(),
            goods.getPrice(),
            goods.getColor()
        );
    }

}
