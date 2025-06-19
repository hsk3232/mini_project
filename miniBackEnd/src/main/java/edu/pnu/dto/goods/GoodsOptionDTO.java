package edu.pnu.dto.goods;

import edu.pnu.domain.GoodsOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsOptionDTO {
	private String optionid;
	private String imgname;
	private String fullcode;
	private String size;
	private Integer stock;
	
	public static GoodsOptionDTO fromEntity(GoodsOption option) {
        return GoodsOptionDTO.builder()
        		.optionid(option.getOptionid())
        		.imgname(option.getImgname())
                .size(option.getSize())
                .stock(option.getStock())
                .fullcode(option.getFullcode())
                .build();
    }
}
