package edu.pnu.dto;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class MainPageGoodsDTO { 
	
	// ------ 메인에 쓰이는 인기/추천 상품 DTO --------- //
	
	private String fullcode;
	private String productName;
	private String imgname;
	private int price;
	private int viewcount;

	public static MainPageGoodsDTO fromEntity(Goods g) {
		return MainPageGoodsDTO.builder()
				.fullcode(g.getFullcode())
				.productName(g.getProductName())
				.imgname(g.getImgname())
				.price(g.getPrice())
				.viewcount(g.getViewcount())
				.build();
	}
}
