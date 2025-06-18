package edu.pnu.service.member;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService{

	private final MemberRepository memberRepo;
    // private final OrderRepository orderRepo; // 구매내역
    // private final ReviewRepository reviewRepo; // 리뷰
    // private final InquiryRepository inquiryRepo; // 문의글 등

    // 1. 내 정보 조회
    public Member findMemberInfo(String username) {
        return memberRepo.findById(username)
            .orElseThrow(() -> new IllegalArgumentException("[오류] : [회원 정보가 없다.]"));
    }

//    // 2. 내 정보 변경
//    public void updateMemberInfo(String username, MemberUpdateDTO dto) {
//        Member member = findMemberInfo(username);
//        member.updateFromDTO(dto); // 엔티티에 update 메서드 따로 구현
//        memberRepo.save(member);
//    }
//
//    // 3. 내 구매내역 조회
//    public List<Order> findMyOrders(String username) {
//        // return orderRepo.findByMemberUsername(username);
//        return null;
//    }
    
    	// 4. 
//
//    // 5. 내가 쓴 리뷰
//    public List<Review> findMyReviews(String username) {
//        // return reviewRepo.findByMemberUsername(username);
//        return null;
//    }
//
//    // 6. 내 문의글
//    public List<Inquiry> findMyInquiries(String username) {
//        // return inquiryRepo.findByMemberUsername(username);
//        return null;
//    }
	
}
