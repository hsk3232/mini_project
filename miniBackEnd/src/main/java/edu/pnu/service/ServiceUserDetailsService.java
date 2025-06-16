package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

// --------------- 로그인 인가 ---------------- //
//Spring Security가 로그인 시 사용자의 정보를 로드할 때 이 클래스를 사용해 DB에서 사용자 정보를 가져옴
//implements UserDetailsService : Spring Security에서 제공하는 사용자 인증 처리 인터페이스

@Service
public class ServiceUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepository memRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memRepo.findById(username)
				.orElseThrow(()->new UsernameNotFoundException("가입자가 아닌디요?!"));
		return new User(member.getUsername(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}

}
