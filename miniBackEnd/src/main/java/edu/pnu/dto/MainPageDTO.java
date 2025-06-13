package edu.pnu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class MainPageDTO {
    private List<String> bannerImg;
    private List<CategoryMainDTO> categoryTree;
    private List<MainPageGoodsDTO> popularItems;
    private List<MainPageGoodsDTO> recommendedItems;
    private List<CategoryDTO> categories;
    private List<GoodsSearchDTO> searchResults;
}
