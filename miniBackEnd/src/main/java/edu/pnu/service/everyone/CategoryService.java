package edu.pnu.service.everyone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.dto.category.CategoryDTO;
import edu.pnu.dto.category.CategoryListResponseDTO;
import edu.pnu.dto.category.CategoryMainDTO;
import edu.pnu.dto.category.CategoryMidDTO;
import edu.pnu.dto.category.CategoryTreeResponseDTO;
import edu.pnu.persistence.GoodsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final GoodsRepository goodsRepo;

	// 1. 카테고리 Tree
	public CategoryTreeResponseDTO getCategoryTrees(String keyword) {
		System.out.println("[진입] : [CategoryService] 카테고리Tree 진입");
		
		Map<String, Map<String, Set<String>>> map = new LinkedHashMap<>();
		for (Goods g : goodsRepo.findAll()) {
			map.computeIfAbsent(g.getMain(), k -> new LinkedHashMap<>())
					.computeIfAbsent(g.getMid(), k -> new LinkedHashSet<>()).add(g.getDetail());
		}

		List<CategoryMainDTO> categoryTree = new ArrayList<>();
		for (String main : map.keySet()) {
			List<CategoryMidDTO> midList = new ArrayList<>();
			for (String mid : map.get(main).keySet()) {
				List<String> details = new ArrayList<>(map.get(main).get(mid));
				midList.add(new CategoryMidDTO(mid, details));
			}
			categoryTree.add(new CategoryMainDTO(main, midList));
		}
		System.out.println("[성공] : [CategoryService] 카테고리Tree 로드 완료 \n");
		return CategoryTreeResponseDTO.builder().categoryTree(categoryTree).build();
		
	}

	
	// 2. 제품 카데고리별 상품 목록 / 필터용
	public CategoryListResponseDTO getCategoryList() {
		List<Goods> allGoods = goodsRepo.findAll();
		List<CategoryDTO> categories = new ArrayList<>();
		for (Goods goods : allGoods) {
			categories.add(CategoryDTO.fromEntity(goods));
		}
		return CategoryListResponseDTO.builder().category(categories).build();
	}
	
	


}
