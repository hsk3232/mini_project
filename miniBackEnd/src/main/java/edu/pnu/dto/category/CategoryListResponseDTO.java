package edu.pnu.dto.category;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryListResponseDTO {
	private List<CategoryDTO> category;

}
