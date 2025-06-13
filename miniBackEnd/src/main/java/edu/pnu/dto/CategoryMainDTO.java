package edu.pnu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryMainDTO {
	private String main;
	private List<CategoryMidDTO> midList; //중분류 리스트
	
}
