package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.goods.QnAGoodsInfoDTO;
import edu.pnu.dto.goods.QnAListDTO;
import edu.pnu.dto.goods.QnAPatchDTO;
import edu.pnu.dto.goods.QnAaddRequestDTO;
import edu.pnu.dto.goods.WishListDTO;
import edu.pnu.dto.member.MemberInfoDTO;
import edu.pnu.dto.member.MemberResponseDTO;
import edu.pnu.dto.member.PasswordChangeRequestDTO;
import edu.pnu.dto.orders.OrderAddressDTO;
import edu.pnu.dto.orders.ReviewAddRequestDTO;
import edu.pnu.dto.orders.ReviewListDTO;
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
	public ResponseEntity<MemberResponseDTO> updateMemberInfo(Principal principal, @RequestBody MemberInfoDTO dto) {
		System.out.println("[진입] : [MemberController] 기본 정보 수정 진입 ");
		String username = principal.getName();
		MemberResponseDTO response = memberService.updateMemberInfo(username, dto);
		System.out.println("[성공] : [MemberController] 기본 정보 수정 성공 ");
		return ResponseEntity.ok(response);
	}

	// 2-2. 비밀번호 변경
	@PatchMapping("/password")
	public ResponseEntity<String> changePassword(Principal principal, @RequestBody PasswordChangeRequestDTO req) {
	    String username = principal.getName();
	    memberService.changePassword(username, req.getCurrentPassword(), req.getNewPassword());
	    return ResponseEntity.ok("비밀번호 변경 완료");
	}

	// 2-3-1. 배송지 목록 확인
	@GetMapping("/address")
	public ResponseEntity<List<OrderAddressDTO>> getMyAddresses(Principal principal) {
		System.out.println("[진입] : [MemberController] 배송지 내역 확인 진입 ");
		List<OrderAddressDTO> dto = memberService.getMyAddresses(principal.getName());
		
		System.out.println("[성공] : [MemberController] 배송지 내역 확인 성공 ");
		return ResponseEntity.ok(dto);
	}

	// 2-3-2. 배송지 추가
	@PostMapping("/addressAdd")
	public OrderAddressDTO addMyAddresses(Principal principal, @RequestBody OrderAddressDTO newAddr) {
		System.out.println("[진입] : [MemberController] 배송지 추가 진입 ");
		OrderAddressDTO dto = memberService.addMyAddresses(principal.getName(), newAddr);

		System.out.println("[성공] : [MemberController] 배송지 추가 성공 ");

		return dto;
	}

	// 2-3-3. 배송지 수정
	@PatchMapping("/addressUpdate")
	public OrderAddressDTO updateMyAddresses(Principal principal, @RequestBody OrderAddressDTO newAddr) {
		System.out.println("[진입] : [MemberController] 배송지 수정 진입 ");
		OrderAddressDTO dto = memberService.updateMyAddresses(principal.getName(), newAddr);
		System.out.println("[성공] : [MemberController] 배송지 수정 성공 ");
		return dto;
	}

	// 2-3-4 배송지 삭제
	@PatchMapping("/addressDelete")
	public List<OrderAddressDTO> deleteMyAddresses(Principal principal, @RequestBody Long addressId) {
		System.out.println("[진입] : [MemberController] 배송지 삭제 진입 ");
		List<OrderAddressDTO> newList = memberService.deleteMyAddress(principal.getName(), addressId);
		System.out.println("[성공] : [MemberController] 배송지 삭제 성공 ");
		return newList;
	}

	
	
	// 4. 내가 쓴 리뷰 조회
	@GetMapping("/reviewsList")
	public List<ReviewListDTO> getMyReviews(Principal principal) {
	    System.out.println("[진입] : [MemberController] review리스트 진입 ");
	    List<ReviewListDTO> reviewList = memberService.getMyReviews(principal.getName());
	    System.out.println("[성공] : [MemberController] review리스트 성공");
	    return reviewList;
	}
	

	// 리뷰 추가
	@PostMapping("/addreviews")
	public List<ReviewListDTO> addMyReviewList(Principal principal, @RequestBody ReviewAddRequestDTO req) {
	    String username = principal.getName();
	    List<ReviewListDTO> reviewList = memberService.addMyReviewList(
	    	    username,
	    	    req.getOrderid(),
	    	    req.getOptionid(),
	    	    req.getReviewtext(),
	    	    req.getRating()
	    	);
	    return reviewList;
	}

	// 리뷰 삭제
	@PatchMapping("/deletereviews")
	public List<ReviewListDTO> deleteMyReviewList(Principal principal, @RequestParam Long orderid) {
	    String username = principal.getName();
	    List<ReviewListDTO> list = memberService.deleteMyReviewList(username, orderid);
	    return list;
	}
	

	// 5. wish 목록
	@GetMapping("/wish")
	public List<WishListDTO> getMyWishList(Principal principal) {
		System.out.println("[진입] : [MemberController] Wish리스트 진입 ");
		String username = principal.getName();
		List<WishListDTO> wishList = memberService.getMyWishList(username);
		System.out.println("[성공] : [MemberController] Wish리스트 성공");
		return wishList;
	}

	// 6-1. Wish 추가
	@PostMapping("/addwish")
	public List<WishListDTO> addMyWishList(Principal principal, @RequestBody String imgname) {
		System.out.println(imgname);
		System.out.println("[진입] : [MemberController] Wish리스트 추가 진입 ");
		String username = principal.getName();
		List<WishListDTO> wishList = memberService.addMyWishList(username, imgname);
		System.out.println("[성공] : [MemberController] Wish리스트 추가 성공");
		return wishList;
	}

	// 6-2. Wish 삭제
	@DeleteMapping("/deletewish")
	public List<WishListDTO> deleteMyWishList(Principal principal, @RequestParam String imgname) {
	    System.out.println("[진입] : [MemberController] Wish 삭제 진입 ");
	    String username = principal.getName();
	    List<WishListDTO> list = memberService.deleteMyWishList(username, imgname);
	    return list;
	}
	
	// 6-3. WishList 유지
	@GetMapping("/heartOn")
	public boolean heartOn(Principal principal, @RequestParam String imgname) {
		System.out.println("[진입] : [MemberController] Wish리스트 on/off 진입 ");
		String username = principal.getName();
		boolean remain = true;
		boolean result = memberService.heartOn(username, imgname, remain);

		return result;
	}
	
	// 4. qna 목록 조회
		@GetMapping("/qnalist")
		public List<QnAListDTO> getMyQnA(Principal principal) {
		    System.out.println("[진입] : [MemberController] QnA리스트 진입 ");
		    List<QnAListDTO> QnAList = memberService.getMyQnA(principal.getName());
		    System.out.println("[성공] : [MemberController] QnA리스트 성공");
		    return QnAList;
		}
		
		
		// qna 상품 정보
		@GetMapping("/qnagoodsinfo")
		public QnAGoodsInfoDTO getGoodsInfo(@RequestBody String imgname) {
			System.out.println("[진입] : [MemberController] QnA 상품정보 수령 진입");
			QnAGoodsInfoDTO info = memberService.getGoodsInfo(imgname);
			System.out.println("[성공] : [MemberController] QnA 상품정보 수령 성공");
			return info;
		}

		// qna 추가 - DBMS에 입력하는 부분으로 DTO 필요 없음
		@PostMapping("/addqna")
		public List<QnAListDTO> addMyQnA(Principal principal, @RequestBody QnAaddRequestDTO q) {
		    System.out.println("[진입] : [MemberController] QnA 작성 진입 ");
		    String username = principal.getName();
		    List<QnAListDTO> result = memberService.addMyQnA(
		        username,
		        q.getImgname(),
		        q.getQuestion()
		    );
		    System.out.println("[성공] : [MemberController] QnA 작성 성공 ");
		    return result;
		}

		// 리뷰 삭제 Patch 님은 무조건 DTO로 전달해야 안전하단다.. 참나
		@PatchMapping("/deleteqna")
		public List<QnAListDTO> deleteMyQnA(Principal principal, @RequestBody QnAPatchDTO p) {
			System.out.println("[진입] : [MemberController] QnA 삭제 진입 ");
			String username = principal.getName();
			List<QnAListDTO> del = memberService.deleteMyQnA(username, p.getQaid());
		    System.out.println("[성공] : [MemberController] QnA 삭제 성공 ");
		    return del;
		}

}
