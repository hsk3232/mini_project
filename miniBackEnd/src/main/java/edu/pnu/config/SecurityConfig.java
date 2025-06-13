package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;

@Configuration
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private MemberRepository memberRepository;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 보호 비활성화
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/public/**").permitAll()
				.requestMatchers("/member/**").authenticated().requestMatchers("/seller/**").hasRole("SELLER")
				.requestMatchers("/manager/**").hasRole("MANAGER")

		);
		// Form을 이용한 로그인을 사용하지 않겠다는 명시적 설정
		// UsernamePasswordAuthenticationFilter가 현재 없지만 명시적 제거
		http.formLogin(frmLogin -> frmLogin.disable());

		// Http Basic인증 방식을 사용하지 않겠다는 명시적 설정
		// BasicAuthenticationFilter가 현재 없지만 명시적 제거
		http.httpBasic(basic -> basic.disable());
		// 세션을 유지하지 않겠다고 설정 (SessionManagementFilter 등록)
		// Url 호출 뒤 응답할 때 까지는 유지되지만 응답 후 삭제
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 스프링 시큐리티의 필터체인에 작성한 필터를 추가한다. UsernamePasswordAuthenticationFilter를 상속한 필터이므로
		// 원래 UsernamePasswordAuthenticationFilter가 위치하는 곳에 대신 추가된다.
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));

		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);
		http.cors(cors -> cors.configurationSource(corsSource()));

		return http.build();
	}
	
	//Front 접속 허용
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("http://localhost:5173"); // 요청을 허용할 서버
		config.addAllowedMethod(CorsConfiguration.ALL); // 요청을 허용할 Method
		config.addAllowedHeader(CorsConfiguration.ALL); // 요청을 허용할 Header
		config.setAllowCredentials(true); // 요청/응답에 자격증명정보/쿠키 포함을 허용
		// true인 경우 addAllowedOrigin("*")는 사용 불가
		// ➔ Pattern으로 변경하거나 허용되는 출처를 명시해야 함.
		config.addExposedHeader(HttpHeaders.AUTHORIZATION); // Header에 Authorization을 추가하기 위해서는 필요
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // 위 설정을 적용할 Rest 서버의 URL 패턴 설정
		return source;
	}

}
