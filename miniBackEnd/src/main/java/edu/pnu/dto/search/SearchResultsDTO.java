package edu.pnu.dto.search;

import java.util.List;

import edu.pnu.dto.goods.GoodsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class SearchResultsDTO {
	private List<GoodsDTO> searchResults;
}
