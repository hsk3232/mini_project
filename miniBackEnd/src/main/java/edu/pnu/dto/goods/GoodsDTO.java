package edu.pnu.dto.goods;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {
	private String imgname;
	private String productName;
    private int price;
    private String color;
    private String gender;
    private String print;

    public static GoodsDTO from(Goods goods) {
    	return GoodsDTO.builder()
    	        .imgname(goods.getImgname())
    	        .productName(goods.getProductName())
    	        .price(goods.getPrice())
    	        .color(goods.getColor())
    	        .gender(goods.getGender())
    	        .print(goods.getPrint())
    	        .build();

    }

}
