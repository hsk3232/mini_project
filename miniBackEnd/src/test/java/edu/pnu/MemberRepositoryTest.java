package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	public void testInserUser() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Member member = new Member();
		
		member.setUsername("user");
		member.setPassword(encoder.encode("1234"));
		member.setRole(Role.ROLE_MEMBER);
		
		memberRepo.save(member);
	}
}
