package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.filter.SearchFilterDTO;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.dto.search.SearchResultsDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.everyone.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class SearchController {

	private final SearchService searchService;
	private final MemberRepository memberRepo;
	
	@GetMapping("/search")
		public SearchResultsDTO getKeywordSearchResults(
				@RequestParam(required = false, defaultValue = "")String keyword,
				Principal principal){
		 Member member = null;
		    if (principal != null) {
		        String username = principal.getName();
		        member = memberRepo.findByUsername(username).orElse(null);
		    }

		    return searchService.getKeywordSearchResults(keyword, member); 
	}
	
	 @PostMapping("/filter")
	    public List<GoodsDTO> filterSearch(@RequestBody SearchFilterDTO dto, Principal principal) {
		 // @RequestBody: 클라이언트가 보낸 JSON을 java 객체로 변환해줌
		 Member member = null;
		    if (principal != null) {
		        member = memberRepo.findByUsername(principal.getName()).orElse(null);
		    }

		    return searchService.getFilteredSearchResults(dto, member);
		}
	 
	// 필터 검색 (GET 방식)
		@GetMapping("/filter")
		public List<GoodsDTO> filterSearchGet(
				@RequestParam(required = false) String main,
				@RequestParam(required = false) String mid,
				@RequestParam(required = false) String detail,
				@RequestParam(required = false) List<String> gender,
				@RequestParam(required = false) List<String> color,
				@RequestParam(required = false) List<String> print,
				@RequestParam(required = false) Integer minPrice,
				@RequestParam(required = false) Integer maxPrice,
				Principal principal) {

			Member member = null;
			if (principal != null) {
				member = memberRepo.findByUsername(principal.getName()).orElse(null);
			}

			// 쿼리 파라미터를 DTO로 수동 매핑
			SearchFilterDTO dto = new SearchFilterDTO();
			dto.setMain(main);
			dto.setMid(mid);
			dto.setDetail(detail);
			dto.setGender(gender);
			dto.setColor(color);
			dto.setPrint(print);
			dto.setMinPrice(minPrice);
			dto.setMaxPrice(maxPrice);

			return searchService.getFilteredSearchResults(dto, member);
		}
}
