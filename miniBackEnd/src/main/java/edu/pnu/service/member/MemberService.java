package edu.pnu.service.member;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import edu.pnu.domain.ReviewList;
import edu.pnu.domain.WishList;
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.Orders.ReviewListDTO;
import edu.pnu.dto.goods.WishListDTO;
import edu.pnu.dto.member.MemberInfoDTO;
import edu.pnu.dto.member.MemberResponseDTO;
import edu.pnu.persistence.GoodsRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.OrderAddressRepository;
import edu.pnu.persistence.OrderListRepository;
import edu.pnu.persistence.ReviewRepository;
import edu.pnu.persistence.WishListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepo;
	private final OrderListRepository orderListRepo; // 구매내역
	private final OrderAddressRepository orderAddrRepo;
	private final ReviewRepository reviewRepo; // 리뷰
	private final WishListRepository wishListRepo;// 문의글 등
	private final PasswordEncoder passwordEncoder; // 비밀번호 인코딩
	private final GoodsRepository goodsRepo;

	// 1. 내 정보 조회
	@Transactional
	public MemberResponseDTO getMemberInfo(String username) {

		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));
		return MemberResponseDTO.fromEntity(member);
	}

	
	// 2. 기본 정보 변경
	public MemberResponseDTO updateMemberInfo(String username, MemberInfoDTO dto) {
		// ✅ 여기서 member는 엔티티
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		member.setNickname(dto.getNickname());
		member.setBirth(dto.getBirth());
		member.setGender(dto.getGender());
		member.setPhone(dto.getPhone());
		member.setEmail(dto.getEmail());
		memberRepo.save(member);

		return MemberResponseDTO.fromEntity(member);
	}

	
	// 3. 비밀번호 변경 로직
	@Transactional
	public void changePassword(String username, String newPassword) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));
		// 🔐 비밀번호 암호화 후 저장
		member.setPassword(passwordEncoder.encode(newPassword));
		memberRepo.save(member);
	}

	
	// 4-1. 배송지 목록 조회
	@Transactional
	public List<OrderAddressDTO> getMyAddresses(String username) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		// 삭제되지 않은 것만 반환
		List<OrderAddress> addresses = orderAddrRepo.findAllByMemberAndDeleteAddrFalse(member);
		if (addresses.isEmpty()) {
			return List.of();
		}

		return addresses.stream().map(OrderAddressDTO::fromEntity).toList();
	}

	
	
	// 4-2. 배송지 추가
	@Transactional // 변경된 값 즉시 감지
	public OrderAddressDTO addMyAddresses(String username, OrderAddressDTO newAddr) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		if (newAddr.getName() == null || newAddr.getZip() == null) {
			throw new IllegalArgumentException("[오류] 주소 데이터를 모두 채워 주세요");
		}

		OrderAddress addr = OrderAddress.builder().member(member).name(newAddr.getName()).zip(newAddr.getZip())
				.address1(newAddr.getAddress1()).address2(newAddr.getAddress2()).phone(newAddr.getPhone())
				.main(newAddr.isMain()).build();

		OrderAddress saved = orderAddrRepo.save(addr);
		System.out.println("[저장 성공] 저장된 Addr: " + saved);
		return OrderAddressDTO.fromEntity(saved);
	}

	
	// 4-3. 배송 주소 수정
	@Transactional
	public OrderAddressDTO updateMyAddresses(String username, OrderAddressDTO newAddr) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		// ✅ 변경 대상 주소를 addressId로 조회
		OrderAddress addr = orderAddrRepo.findById(newAddr.getAddressId())
				.orElseThrow(() -> new IllegalArgumentException("[오류] 주소 정보가 없습니다: " + newAddr.getAddressId()));

		// ✅ 내 것이 아닐 경우 예외
		if (!addr.getMember().getUsername().equals(username)) {
			throw new IllegalArgumentException("[오류] 다른 회원의 주소를 변경할 수 없습니다.");
		}

		// ✅ 다른 주소 모두 main = false
		for (OrderAddress a : member.getAddresses()) {
			a.setMain(false);
		}

		// ✅ 변경 로직
		addr.setName(newAddr.getName());
		addr.setZip(newAddr.getZip());
		addr.setAddress1(newAddr.getAddress1());
		addr.setAddress2(newAddr.getAddress2());
		addr.setPhone(newAddr.getPhone());
		addr.setMain(newAddr.isMain());

		// ✅ 저장 후 반환
		return OrderAddressDTO.fromEntity(addr);
	}
	

	// 4-4. 배송 주소 삭제
	@Transactional
	public List<OrderAddressDTO> deleteMyAddress(String username, Long addressId) {
		OrderAddress addr = orderAddrRepo.findById(addressId)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 주소 정보가 없습니다: " + addressId));

		addr.setDeleteAddr(true);
		orderAddrRepo.save(addr);
		return getMyAddresses(username);
	}
	
	

	// 리뷰 목록 조회
	public List<ReviewListDTO> getMyReviews(String username) {
		return reviewRepo.findAllByMember_Username(username)
		        .stream()
		        .filter(r -> r.isRemain())
		        .map(ReviewListDTO::fromEntity)
		        .toList();
	}

	// 리뷰 추가 (orderid 기준으로 예시, imgname으로 추가하고 싶다면 구조 조정 필요)
	public List<ReviewListDTO> addMyReviewList(String username, Long orderid, String optionid, String reviewtext, int rating) {
	    // 1. 유저 검증
	    Member member = memberRepo.findByUsername(username)
	        .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
	    // 2. 주문 검증
	    OrderList order = orderListRepo.findById(orderid)
	        .orElseThrow(() -> new IllegalArgumentException("주문 없음"));
	    // 3. 해당 옵션의 주문 항목(OrderItem) 찾기
	    OrderItem targetItem = order.getItems().stream()
	        .filter(i -> i.getGoodsOption().getOptionid().equals(optionid))
	        .findFirst()
	        .orElseThrow(() -> new IllegalArgumentException("해당 옵션의 주문 항목이 없음"));
	    // 4. 옵션과 상품 꺼내기
	    GoodsOption option = targetItem.getGoodsOption();
	    Goods goods = option.getGoods();

	    // 5. 중복 리뷰 체크 (상품 옵션별로만 리뷰 하나씩 허용)
	    boolean alreadyReviewed = reviewRepo.existsByMember_UsernameAndOrderList_OrderidAndGoodsOption_Optionid(username, orderid, optionid);
	    if (!alreadyReviewed) {
	        ReviewList review = ReviewList.builder()
	            .member(member)
	            .orderList(order)
	            .goods(goods)
	            .goodsOption(option)
	            .reviewtext(reviewtext)
	            .rating(rating)
	            .createdat(LocalDateTime.now())
	            .remain(true)
	            .build();

	        reviewRepo.save(review);
	    }
	    return getMyReviews(username);
	}

	// 리뷰 논리 삭제
	public List<ReviewListDTO> deleteMyReviewList(String username, Long orderid) {
	    ReviewList review = reviewRepo.existsByMember_UsernameAndOrderList_Orderid(username, orderid);
	    review.setRemain(false);
	    reviewRepo.save(review);
	    return getMyReviews(username);
	}

	
	

	// 6-1. wishList 조회
	public List<WishListDTO> getMyWishList(String username) {
	    return wishListRepo.findByMemberUsername(username).stream()
	        .filter(WishList::isRemain)  // remain==true인 것만 남김
	        .map(WishListDTO::fromEntity)
	        .toList();
	}

	
	// 6-2. wishList 추가
	public List<WishListDTO> addMyWishList(String username, String imgname) {
		// 1. 중복 체크
		boolean exists = wishListRepo.existsByMember_UsernameAndGoods_Imgname(username, imgname);
		if (!exists) {
			Member member = memberRepo.findByUsername(username).orElseThrow();
			Goods goods = goodsRepo.findByImgname(imgname);
			WishList wish = WishList.builder().member(member).goods(goods).remain(true) // 논리 삭제라면 true로 기본 설정
					.build();
			wishListRepo.save(wish);
		}
		// 2. 항상 현재 목록 반환
		return wishListRepo.findByMemberUsername(username).stream().map(WishListDTO::fromEntity).toList();
	}

	
	
	
	// 6-3. wishList 삭제
	public List<WishListDTO> deleteMyWishList(String username, String imgname) {
	    WishList wish = wishListRepo.findByMember_UsernameAndGoods_Imgname(username, imgname);
	    if (wish == null) {
	        throw new IllegalArgumentException("해당 찜이 존재하지 않습니다.");
	    }
	    wishListRepo.delete(wish);

	    return wishListRepo.findByMemberUsername(username).stream()
	    	.filter(WishList::isRemain)
	        .map(WishListDTO::fromEntity)
	        .toList();
	}

	
	
	// 6-4. wishList on/ off
	public boolean heartOn(String username, String imgname, boolean remain) {
	    return wishListRepo.existsByMember_UsernameAndGoods_ImgnameAndRemain(username, imgname, remain);
	}
}
