package edu.pnu.service.member;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.dto.member.MemberJoinDTO;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {
	
	private final MemberRepository memberRepo;
	
	public void postJoinsave(MemberJoinDTO dto) {
		 Member member = dto.toEntity();
	        memberRepo.save(member);
	}

	
}
