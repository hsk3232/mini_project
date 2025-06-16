package edu.pnu.dto.goods;

import java.util.List;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class AdGoodsDTO { 
	
	// ------ 메인에 쓰이는 인기/추천 상품 DTO --------- //
	
	public static AdGoodsDTO fromEntity(Goods goods) {
		GoodsDTO g = new GoodsDTO();
		
	}

	
}
