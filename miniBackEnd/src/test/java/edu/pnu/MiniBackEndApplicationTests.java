package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.persistence.MemberRepository;

@SpringBootTest
class MiniBackEndApplicationTests {
	
	@Autowired
	private MemberRepository repo;
	
	@Test
	void contextLoads() {
		System.out.println(repo.existsById("user"));
	}
	

}
