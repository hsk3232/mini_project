package edu.pnu.dto.category;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {
	private String main;
	private String mid;
	private String detail;
	private String categorycode;

	public static CategoryDTO fromEntity(Goods g) {
		return CategoryDTO.builder()
				.main(g.getMain())
				.mid(g.getMid())
				.detail(g.getDetail())
				.categorycode(g.getCategorycode())
				.build();
	}
}
