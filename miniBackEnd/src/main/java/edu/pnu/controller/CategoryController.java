package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.category.CategoryListResponseDTO;
import edu.pnu.dto.category.CategoryTreeResponseDTO;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.service.everyone.CategoryService;
import edu.pnu.service.everyone.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class CategoryController {

	private final CategoryService categoryService;
	private final SearchService searchService;

	@GetMapping("/categoryTree")
	public CategoryTreeResponseDTO getCategoryTrees(@RequestParam(required = false, defaultValue = "") String keyword) {
		return categoryService.getCategoryTrees(keyword);
	}

	@GetMapping("/category")
	public CategoryListResponseDTO getCategoryList() {
		return categoryService.getCategoryList();
	}

	// 상품 카테고리 필터 조회용: 메인 > (중분류) > (소분류)
	@GetMapping("/category/goods")
	public List<GoodsDTO> filterGoods(
			@RequestParam(required = false) String main,
			@RequestParam(required = false) String mid, 
			@RequestParam(required = false) String detail,
			@RequestParam(required = false) List<String> gender, 
			@RequestParam(required = false) List<String> color,
			@RequestParam(required = false) List<String> print, 
			@RequestParam(required = false) Integer minPrice,
			@RequestParam(required = false) Integer maxPrice, Principal principal

	) {
		SearchFilterDTO filter = SearchFilterDTO.builder().main(main).mid(mid).detail(detail).gender(gender)
				.color(color).print(print).minPrice(minPrice).maxPrice(maxPrice).build();
				
		 return searchService.getFilteredSearchResults(filter);
	}
}
