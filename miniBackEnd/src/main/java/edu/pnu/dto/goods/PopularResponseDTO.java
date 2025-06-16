package edu.pnu.dto.goods;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class PopularResponseDTO {
	private List<AdGoodsDTO> popularItems;
}
