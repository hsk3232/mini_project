package edu.pnu.service.member;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.Orders.OrderListDTO;
import edu.pnu.dto.member.MemberInfoDTO;
import edu.pnu.dto.member.MemberResponseDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.OrderAddressRepository;
import edu.pnu.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepo;
	private final OrderRepository orderRepo; // 구매내역
	private final OrderAddressRepository orderAddrRepo;
	// private final ReviewRepository reviewRepo; // 리뷰
	private final WishListRepository wishListRepo; // 문의글 등
	private final PasswordEncoder passwordEncoder; // 비밀번호 인코딩

	// 1. 내 정보 조회
	public MemberResponseDTO getMemberInfo(String username) {

		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));
		return MemberResponseDTO.fromEntity(member);

	}

	// 2-1. 기본 정보 변경
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

	// 2-2. 비밀번호 변경 로직
	public void changePassword(String username, String newPassword) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));
		// 🔐 비밀번호 암호화 후 저장
		member.setPassword(passwordEncoder.encode(newPassword));
		memberRepo.save(member);

	}

	// 2-3-1. 배송지 확인
	public List<OrderAddressDTO> getMyAddresses(String username) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));
		List<OrderAddress> addresses = orderAddrRepo.findAllByMember(member);
		if (addresses.isEmpty()) {
			// 예외 대신 빈 리스트 반환
			return List.of();
		}

		return addresses.stream().map(OrderAddressDTO::fromEntity).toList();
	}

	// 2-3-2. 배송지 추가
	public OrderAddressDTO addMyAddresses(String username, OrderAddressDTO newAddr) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		if (newAddr.getName() == null || newAddr.getZip() == null) {
			throw new IllegalArgumentException("[오류] 주소 데이터를 모두 채워 주세요");
		}

		OrderAddress addr = OrderAddress.builder().member(member).name(newAddr.getName()).zip(newAddr.getZip())
				.address1(newAddr.getAddress1()).address2(newAddr.getAddress2()).phone(newAddr.getPhone())
				.isMain(newAddr.isMain()).build();

		OrderAddress saved = orderAddrRepo.save(addr);
		System.out.println("[저장 성공] 저장된 Addr: " + saved);
		return OrderAddressDTO.fromEntity(saved);
	}

	// 2-3-3. 배송지 수정
//2-3-3. 배송지 수정 
	public OrderAddressDTO updateMyAddresses(String username, OrderAddressDTO newAddr) {
		Member member = memberRepo.findById(username)
				.orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보가 없습니다: " + username));

		OrderAddress addr = orderAddrRepo.findById(newAddr.getAddressId())
				.orElseThrow(() -> new IllegalArgumentException("[오류] 주소 정보가 없습니다: " + newAddr.getAddressId()));

		// ✅ 내 것이 아닐 경우 예외
		if (!addr.getMember().getUsername().equals(username)) {
			throw new IllegalArgumentException("[오류] 다른 회원의 주소를 변경할 수 없습니다.");
		}

		// ✅ 변경 로직
		addr.setName(newAddr.getName());
		addr.setZip(newAddr.getZip());
		addr.setAddress1(newAddr.getAddress1());
		addr.setAddress2(newAddr.getAddress2());
		addr.setPhone(newAddr.getPhone());
		addr.setMain(newAddr.isMain());

		orderAddrRepo.save(addr);

		return OrderAddressDTO.fromEntity(addr);
	}


// 2-3-3. 배송지 삭제
	public List<OrderAddressDTO> deleteMyAddresses(String username, Long addressId) {
	    OrderAddress addr = orderAddrRepo.findById(addressId)
	        .orElseThrow(() -> new IllegalArgumentException("[오류] 주소 정보가 없습니다: " + addressId));
	    addr.setDeleteAddr(true);
	    orderAddrRepo.save(addr);

	    // ✅ 삭제 후 최신 주소 목록 반환
	    System.out.println("[성공] : [MemberService] 배송지 수정 성공 ");
	    return getMyAddresses(username);
	}

	

	// 3. 내 구매내역 조회
	public List<OrderListDTO> getMyOrders(String username) {
    	
  	    List<OrderList> orderList = orderRepo.findByMemberUsername(username);
    	
  	    
       return orderList.stream().map(OrderListDTO::fromEntity);
       
    }

//	// 5. 내가 쓴 리뷰
//	public List<Review> getMyReviews(String username) {
//		// return reviewRepo.findByMemberUsername(username);
//		return null;
//	}

//	// 6. 내 문의글
//	public List<QnA> getMyQnA(String username) {
//		// return inquiryRepo.findByMemberUsername(username);
//		return null;
//	}

//	// 4. wishList
//	public List<WishList> getMyWishList(String username) {
//		return wishListRepo.findByMemberUsername(username);
//
//	}

}
