package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.member.MemberResponseDTO;
import edu.pnu.dto.member.MemberUpdateDTO;
import edu.pnu.service.everyone.GoodsService;
import edu.pnu.service.member.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {
	
	private final MemberService memberService;


    // 1-1. 내 정보 조회
	@GetMapping("/info")
	public ResponseEntity<MemberResponseDTO> getMemberInfo(@AuthenticationPrincipal Member member) {
	    MemberResponseDTO dto = memberService.toResponseDTO(member);
	    return ResponseEntity.ok(dto);
	}

	
    // 1-2. 내 정보 수정 (PATCH/PUT 권장)
    @PutMapping("/info")
    public String updateMemberInfo(Principal principal, @RequestBody MemberUpdateDTO dto) {
    	String username = principal.getName();  	
        memberService.updateMemberInfo(username, dto);
        
        return "정보 수정 완료";
    }
 
    
    // 2. 배송지 설정
    @GetMapping("/address")
    public ResponseEntity<List<OrderAddressDTO>> getMyAddresses(Principal principal) {
    	Member member = memberService.findMemberInfo(principal.getName());
        List<OrderAddressDTO> list = member.getAddresses().stream()
            .map(OrderAddressDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(list);
    }
    
    
    
    // 3. 내 구매내역
    @GetMapping("/orderlist")
    public List<OrderList> getMyOrders(Principal principal) {
    	Member member = memberService.findMemberInfo(principal.getName());
        return memberService.getMyOrders(member.getUsername());
    }

    
    // 4. 내가 쓴 리뷰
    @GetMapping("/reviews")
    public List<Review> getMyReviews(Principal principal) {
    	Member member = memberService.findMemberInfo(principal.getName());
 
        return memberService.getMyReviews(member.getUsername());
    }

    
    // 5. 내 문의글
    @GetMapping("/qna")
    public List<QnA> getMyQnA(Principal principal) {
    	Member member = memberService.findMemberInfo(principal.getName());
        return memberService.getMyQnA(member.getUsername());
    }
  
    
    // 7. WishList
    @GetMapping("/wishlist")
    public List<WishList> getMyWishList(Principal principal) {
    	Member member = memberService.findMemberInfo(principal.getName());
        return memberService.getMyWishList(member.getUsername());
    }
}
