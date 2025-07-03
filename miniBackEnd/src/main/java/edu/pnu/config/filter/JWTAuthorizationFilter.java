package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Date;
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
import com.auth0.jwt.interfaces.DecodedJWT;

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

		// public 경로는 토큰 확인 없이 무조건 통과
		String uri = request.getRequestURI();
		// ⭐ 1. public 경로는 무조건 통과!
		if (uri.startsWith("/api/public/")) {
			filterChain.doFilter(request, response);
			return;
		}

		System.out.println("[진입] : [JWTAuthorizationFilter] 토큰 확인 필터 진입");

		String srcToken = request.getHeader(HttpHeaders.AUTHORIZATION); // 요청 헤더에서 Authorization을 얻어온다.
		System.out.println("[발행된 토큰] : " + srcToken);

		if (srcToken == null || !srcToken.startsWith("Bearer ")) { // 없거나 “Bearer ”로 시작하지 않는다면
			System.out.println("[진입] : [JWTAuthorizationFilter] 토큰 없음 \n");
			filterChain.doFilter(request, response); // 필터를 그냥 통과
			return;
		}
		System.out.println("[완료] : [JWTAuthorizationFilter] 토큰 확인 완료");

		String jwtToken = srcToken.replace("Bearer ", ""); // 토큰에서 “Bearer ”를 제거
		// 토큰에서 username 추출

		String username = null;
		try {

			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken);
			username = decodedJWT.getClaim("username").asString();

			if (decodedJWT.getExpiresAt().before(new Date())) {
				System.out.println("[진행] : [JWTAuthorizationFilter] username(id) 추출 시작");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				System.out.println("[진행] : [4][JWTAuthorizationFilter] 로그인한 아이디의 토큰 유효기간 만료");
				response.setContentType("text/plain");
				response.getWriter().write("Expired Token");

				// 토큰 만료시, 토큰 재발급 해서 front로 다시 보냄
				String token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 100000))
						.withClaim("username", username).sign(Algorithm.HMAC256("edu.pnu.jwt"));

				System.out.println("[진행] : [4][JWTAuthorizationFilter] 로그인한 아이디의 토큰 재발행 전송 \n");
				response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
				return;
			}
		} catch (Exception e) {
			// 예외가 발생하면 로그 출력 후 필터 체인 그냥 통과
			System.out.println("[오류] : [JWTAuthorizationFilter] JWT 오류 발생" + e.getMessage());

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
			filterChain.doFilter(request, response);
			return;
		}

		if (username == null) {
			// 🔥 토큰에 username이 없을 경우도 예외로 처리
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
			filterChain.doFilter(request, response);
			return;
		}

		Optional<Member> opt = memberRepository.findById(username); // 토큰에서 얻은 username으로 DB를 검색해서 사용자를 검색
		System.out.println("[진행] : [JWTAuthorizationFilter] username(id) 검색 시작");
		if (!opt.isPresent()) { // 사용자가 존재하지 않는다면
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
			filterChain.doFilter(request, response); // 필터를 그냥 통과
			System.out.println("[오류] : [JWTAuthorizationFilter]사용자가 없다.");
			return;
		}
		System.out.println("[진행] : [JWTAuthorizationFilter] 사용자 찾음");

		Member findmember = opt.get();
		System.out.println("[진행] : [JWTAuthorizationFilter] ROLE 값: [" + findmember.getRole() + "]");

		try {
			User user = new User(findmember.getUsername(), findmember.getPassword(),
					AuthorityUtils.createAuthorityList("ROLE_" + findmember.getRole().toString()));
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
			System.out.println("[성공] : [JWTAuthorizationFilter] SecurityContext 등록 완료 \n");
			filterChain.doFilter(request, response);
			System.out.println("[성공] : [JWTAuthorizationFilter] 토큰 확인 완료 \n");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[오류] : [JWTAuthorizationFilter] 예외 발생! \n");
		}
	}
}
