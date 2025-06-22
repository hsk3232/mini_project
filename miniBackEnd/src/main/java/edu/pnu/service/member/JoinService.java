package edu.pnu.service.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.dto.member.MemberJoinDTO;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {

	private final MemberRepository memberRepo;
	private final PasswordEncoder passwordEncoder; // 암호화 주입!
	
	
	public boolean postJoinIdSearch(String username) {
		 // 1. 이미 존재하는 아이디인지 검사
	    boolean ret = memberRepo.existsById(username);
	    return ret;
	    
	}

	public void postJoinSave(MemberJoinDTO dto) {
		 // 1. 이미 존재하는 아이디인지 검사
	    boolean exists = memberRepo.existsById(dto.getUsername());
	    if (exists) {
	        // 2. 이미 있다면 예외 발생!
	        throw new IllegalArgumentException("[경고/오류] :[JoinService] 이미 사용 중인 아이디");
	    }
	    // 3. 비밀번호 암호화 후 저장
	    Member member = dto.toEntity();
	    member.setPassword(passwordEncoder.encode(dto.getPassword()));
	    memberRepo.save(member);
	    System.out.println("[완료] : [JoinService] 아이디 저장 및 비밀번호 암호화 저장 성공 \n");
	}

}
