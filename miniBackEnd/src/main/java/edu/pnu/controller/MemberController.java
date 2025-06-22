package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderList;
import edu.pnu.domain.QnA;
import edu.pnu.domain.Review;
import edu.pnu.domain.WishList;
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.member.MemberInfoDTO;
import edu.pnu.dto.member.MemberResponseDTO;
import edu.pnu.service.member.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {

	private final MemberService memberService;
	 

	// 1-1. 내 정보 조회
	@GetMapping("/info")
	public ResponseEntity<MemberResponseDTO> getMemberInfo(Principal principal) {
		System.out.println("[진입] : [MemberController] 내 정보 조회 진입");

		MemberResponseDTO dto = memberService.getMemberInfo(principal.getName());
		System.out.println("[성공] : [MemberController] 내 정보 조회 성공 ");
		return ResponseEntity.ok(dto);

	}

	// 2-1. 기본 정보 수정
	@PatchMapping("/infoEdit")
	public ResponseEntity<MemberResponseDTO> updateMemberInfo(Principal principal, 
			@RequestBody MemberInfoDTO dto) {
		System.out.println("[진입] : [MemberController] 기본 정보 수정 진입 ");
	    String username = principal.getName();
	    MemberResponseDTO response = memberService.updateMemberInfo(username, dto);
	    System.out.println("[성공] : [MemberController] 기본 정보 수정 성공 ");
	    return ResponseEntity.ok(response);
	}
	
	// 2-2. 비밀번호 변경
	@PatchMapping("/password")
	public ResponseEntity<String> changePassword(Principal principal,
	                                              @RequestBody String newPassword) {
	    String username = principal.getName();
	    memberService.changePassword(username, newPassword);
	    return ResponseEntity.ok("비밀번호 변경 완료");
	}
	
	

	// 2-3-1. 배송지 목록 확인
	@GetMapping("/address")
	public ResponseEntity <List<OrderAddressDTO>> getMyAddresses(Principal principal) {
		System.out.println("[진입] : [MemberController] 배송지 내역 확인 진입 ");
		List<OrderAddressDTO> dto = memberService.getMyAddresses(principal.getName());
		System.out.println("[성공] : [MemberController] 배송지 내역 확인 성공 ");
		return ResponseEntity.ok(dto);
	}
	
	// 2-3-2. 배송지 추가
	@PostMapping("/addressAdd")
	public OrderAddressDTO addMyAddresses (Principal principal, @RequestBody OrderAddressDTO newAddr){
		System.out.println("[진입] : [MemberController] 배송지 추가 진입 ");
		OrderAddressDTO dto = memberService.addMyAddresses(principal.getName(), newAddr);
		
		System.out.println("[성공] : [MemberController] 배송지 추가 성공 ");
		
		return dto;
	}
	
	
	// 2-3-3. 배송지 수정
	@PatchMapping("/addressUpdate")
	public OrderAddressDTO updateMyAddresses (Principal principal, 
			@RequestBody OrderAddressDTO newAddr){
		System.out.println("[진입] : [MemberController] 배송지 수정 진입 ");
		OrderAddressDTO dto = memberService.updateMyAddresses(principal.getName(), newAddr);
		System.out.println("[진입] : [MemberController] 배송지 수정 성공 ");
		return dto;
	}
	
	// 2-3-4 배송지 삭제
	@PatchMapping("/addressDelete")
	public List<OrderAddressDTO> deleteMyAddresses(Principal principal, Long addressId) {
		System.out.println("[진입] : [MemberController] 배송지 삭제 진입 ");
		return memberService.deleteMyAddresses(principal.getName(), addressId);
	}
	
	

	// 3-1. 내 구매내역 조회
	@GetMapping("/orderlist")
	public List<OrderList> getMyOrders(Principal principal) {
		List<OrderList> orderList = memberService.getMyOrders(principal.getName());
		
		
		return memberService.getMyOrders(member.getUsername());
	}
//	
	// 3-2. 구매 내역 취소

//	// 4. 내가 쓴 리뷰
//	@GetMapping("/reviews")
//	public List<Review> getMyReviews(Principal principal) {
//		Member member = memberService.getMyReviews(principal.getName());
//
//		return memberService.getMyReviews(member.getUsername());
//	}

//	// 5. 내 문의글
//	@GetMapping("/qna")
//	public List<QnA> getMyQnA(Principal principal) {
//		String username = principal.getName();
//	    MemberResponseDTO response = memberService.updateMemberInfo(username, dto);
//		Member member = memberService.getMyQnA(principal.getName());
//		return memberService.getMyQnA(member.getUsername());
//	}

//	// 7. WishList
//	@GetMapping("/wishlist")
//	public List<WishList> getMyWishList(Principal principal) {
//		Member member = memberService.getMyWishList(principal.getName());
//		return memberService.getMyWishList(member.getUsername());
//	}

}
