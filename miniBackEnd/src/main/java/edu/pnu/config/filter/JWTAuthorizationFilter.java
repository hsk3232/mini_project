package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final MemberRepository memberRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String srcToken = request.getHeader(HttpHeaders.AUTHORIZATION); // 요청 헤더에서 Authorization을 얻어온다.
		if (srcToken == null || !srcToken.startsWith("Bearer ")) { // 없거나 “Bearer ”로 시작하지 않는다면
			filterChain.doFilter(request, response); // 필터를 그냥 통과
			return;
		}

		String jwtToken = srcToken.replace("Bearer ", ""); // 토큰에서 “Bearer ”를 제거
		// 토큰에서 username 추출
		System.out.println("[username(id) 추출 시작]");

		String username = null;
		try {
			// 토큰 검증 과정 try-catch로 감싸기 (만료된 토큰 등 예외 처리)
			username = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getClaim("username")
					.asString();
		} catch (Exception e) {
			// 예외가 발생하면 로그 출력 후 필터 체인 그냥 통과
			System.out.println("JWT 예외 발생: " + e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}

		if (username == null) {
			// 🔥 토큰에 username이 없을 경우도 예외로 처리
			filterChain.doFilter(request, response);
			return;
		}

		Optional<Member> opt = memberRepository.findById(username); // 토큰에서 얻은 username으로 DB를 검색해서 사용자를 검색
		System.out.println("[username(id) 검색 시작]");
		if (!opt.isPresent()) { // 사용자가 존재하지 않는다면
			filterChain.doFilter(request, response); // 필터를 그냥 통과
			System.out.println("사용자가 없다.");
			return;
		} System.out.println("[사용자 찾음]");
		

		Member findmember = opt.get();
		System.out.println("ROLE 값: " + findmember.getRole());


		try {
		    User user = new User(findmember.getUsername(), findmember.getPassword(),
		        AuthorityUtils.createAuthorityList("ROLE_" + findmember.getRole().toString()));
		    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		    SecurityContextHolder.getContext().setAuthentication(auth);
		    System.out.println("[SecurityContext 등록 완료]");
		    filterChain.doFilter(request, response);
		    System.out.println("[토큰 확인 완료]");
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println("[예외 발생!]");
		}
	}
}
