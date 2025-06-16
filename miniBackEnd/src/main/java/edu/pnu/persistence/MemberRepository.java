package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	//Optional: null 값이면 실행이 안되는 type
	Optional<Member> findByUsername(String username);
}
