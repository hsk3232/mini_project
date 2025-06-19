package edu.pnu.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderList;
import edu.pnu.domain.QnA;
import edu.pnu.domain.Review;
import edu.pnu.domain.WishList;
import edu.pnu.dto.goods.AdGoodsDTO;
import edu.pnu.dto.member.MemberUpdateDTO;
import edu.pnu.service.everyone.GoodsService;
import edu.pnu.service.member.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {
	
	private final MemberService memberService;
	private final GoodsService goodsService;

    // 1-1. 내 정보 조회
    @GetMapping("/info")
    public Member getMemberInfo(@AuthenticationPrincipal Member member) {
        return member; // JWT 인증 기반이면 이렇게도 가능!
        // 또는 memberService.findMemberInfo(member.getUsername());
    }

    // 1-2. 내 정보 수정 (PATCH/PUT 권장)
    @PutMapping("/info")
    public String updateMemberInfo(@AuthenticationPrincipal Member member, 
                                   @RequestBody MemberUpdateDTO dto) {
        memberService.updateMemberInfo(member.getUsername(), dto);
        return "정보 수정 완료";
    }

	
	
    
    
    // 3. 내 구매내역
    @GetMapping("/orderlist")
    public List<OrderList> getMyOrders(@AuthenticationPrincipal Member member) {
        return memberService.getMyOrders(member.getUsername());
    }
//
    // 4. 내가 쓴 리뷰
    @GetMapping("/reviews")
    public List<Review> getMyReviews(@AuthenticationPrincipal Member member) {
        return memberService.getMyReviews(member.getUsername());
    }

    // 5. 내 문의글
    @GetMapping("/qna")
    public List<QnA> getMyQnA(@AuthenticationPrincipal Member member) {
        return memberService.getMyQnA(member.getUsername());
    }
    
 // 7. WishList
    @GetMapping("/wishlist")
    public List<WishList> getMyWishList(@AuthenticationPrincipal Member member) {
        return memberService.getMyWishList(member.getUsername());
    }
}
