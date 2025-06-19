package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.everyone.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class SearchController {

	private final SearchService searchService;
	private final MemberRepository memberRepo;



	// 필터 검색 (GET 방식)
	@GetMapping("/search")
	public List<GoodsDTO> getfilterSearch(
			
			// DTO로 수동 매핑 => @ModelAttribute로 인해 필요 없어짐.
//			@RequestParam(required = false) String main,
//			@RequestParam(required = false) String mid,
//			@RequestParam(required = false) String detail,
//			@RequestParam(required = false) List<String> gender,
//			@RequestParam(required = false) List<String> color,
//			@RequestParam(required = false) List<String> print,
//			@RequestParam(required = false) Integer minPrice,
//			@RequestParam(required = false) Integer maxPrice,

			@ModelAttribute SearchFilterDTO dto, // ✅ 자동 바인딩 처리
			Principal principal, 
			@RequestParam(defaultValue = "newest") String sort) {

		Member member = null;
		if (principal != null) {
			member = memberRepo.findByUsername(principal.getName()).orElse(null);
			System.out.println("[성공] : [SearchController] 회원 정보를 찾았다. \n");
		}

		// 쿼리 파라미터를 DTO로 수동 매핑 => @ModelAttribute로 인해 필요 없어짐.
//			SearchFilterDTO dto = new SearchFilterDTO();
//			dto.setMain(main);
//			dto.setMid(mid);
//			dto.setDetail(detail);
//			dto.setGender(gender);
//			dto.setColor(color);
//			dto.setPrint(print);
//			dto.setMinPrice(minPrice);
//			dto.setMaxPrice(maxPrice);

		return searchService.getfilterSearch(dto, member, sort);
	}
}
