package edu.pnu.dto.goods;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class GoodsSearchDTO {
	private String productName;
	private String imgname;
	private int price;

	public static GoodsSearchDTO fromEntity(Goods g) {
		return GoodsSearchDTO.builder()
				.productName(g.getProductName())
				.imgname("/api/public/img/goods/" + g.getImgname())
				.price(g.getPrice())
				.build();
	}
}
