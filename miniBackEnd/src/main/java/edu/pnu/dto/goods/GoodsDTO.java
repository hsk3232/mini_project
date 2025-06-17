package edu.pnu.dto.goods;

import java.time.LocalDate;

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
    private String viewcount;
    private LocalDate registerdate; // 상품 등록일
	private String size; 				// 사이즈
	private int stock; 				// 제고
	private String description; 
	
	// JSON은 여기서 담은 정보대로 나감.
    public static GoodsDTO fromEntity(Goods goods) {
    	return GoodsDTO.builder()
    	        .imgname(goods.getImgname())
    	        .productName(goods.getProductName())
    	        .price(goods.getPrice())
    	        .color(goods.getColor())
    	        .gender(goods.getGender())
    	        .print(goods.getPrint())
    	        .build();

    }
    
    public static GoodsDTO fromEntity(Goods g, int stock, String description) {
    	return GoodsDTO.builder()
    			.imgname(g.getImgname())
                .productName(g.getProductName())
                .price(g.getPrice())
                .color(g.getColor())
                .gender(g.getGender())
                .print(g.getPrint())
                .stock(stock)
                .description(description)
                .build();
    }

}
