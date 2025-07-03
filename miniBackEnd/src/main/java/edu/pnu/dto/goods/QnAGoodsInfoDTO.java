package edu.pnu.dto.goods;

import edu.pnu.domain.Goods;
import edu.pnu.domain.QnA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnAGoodsInfoDTO {
	    private String imgname;
	    private String productname;
	    private Integer price;

	    // Goods 엔티티에서 DTO로 변환하는 정적 메서드
	    public static QnAGoodsInfoDTO fromEntity(Goods goods) {
	        return QnAGoodsInfoDTO.builder()
	            .imgname(goods.getImgname())
	            .productname(goods.getProductName())
	            .price(goods.getPrice())
	            .build();
	    }
}
