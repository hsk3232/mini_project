package edu.pnu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryMidDTO {
	private String mid;
	private List<String> detailList; //소분류 리스트
}
