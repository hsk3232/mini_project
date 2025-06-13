package edu.pnu.controll;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.GoodsDTO;
import edu.pnu.dto.MainPageDTO;
import edu.pnu.service.GoodsService;
import edu.pnu.service.MainPageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class MainPageController {

	private final MainPageService mainPageService;

	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/home") //Principal principal 로그인한 사용자 spring security가 자동으로 넣어줌
	    public MainPageDTO getMainPage(@RequestParam(required = false, defaultValue = "")String keyword, Principal principal) {
		
        // 사용자 인증 객체에서 Member 조회 (직접 구현했을 경우 예시)
        Member member = null;
        if (principal != null) {
            String username = principal.getName();
            // 이 예시는 직접 MemberRepository에서 가져온다고 가정
            member = new Member(); 
            member.setUsername(username); 
            // 실제로는 memberService
        }

        return mainPageService.getMainPage(keyword, member);
    }
	
	@GetMapping("/category")
	public List<GoodsDTO> getGoods(
	    @RequestParam String main,
	    @RequestParam(required = false) String mid,
	    @RequestParam(required = false) String detail
	) {
	    return goodsService.findGoods(main, mid, detail);
	}
}
