package edu.pnu.dto.filter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFilterDTO {
		private String keyword; // 검색어
	    private String main;
	    private String mid;
	    private String detail;
	    private List<String> gender;
	    private List<String> color;
	    private List<String> print;
	    private Integer minPrice;
	    private Integer maxPrice;
	    
}
