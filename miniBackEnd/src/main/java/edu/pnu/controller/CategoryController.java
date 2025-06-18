package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public List<GoodsDTO> getCategoryfilterAndSearchAndSort(
		    @ModelAttribute SearchFilterDTO filter, // 자동 바인딩 (keyword 미포함 시 null)
		    Principal principal,
		    @RequestParam(defaultValue = "register") String sort
		) {
		    return searchService.getfilterSearch(filter, null, sort);
		}
}
