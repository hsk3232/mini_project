package edu.pnu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.member.MemberJoinDTO;
import edu.pnu.service.member.JoinService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class JoinController {
	
	private final JoinService joinService;
		
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberJoinDTO dto) {
		try {
			joinService.postJoinSave(dto);
	        return ResponseEntity.ok("[성공] : [JoinController] 회원 가입 성공 \n");
	    } catch (IllegalArgumentException e) {
	        // 메시지만 응답
	    	System.out.println("[경고/오류] : [JoinController] 이미 사용 중인 아이디입니다. \n");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	    }
	}
	
	@PostMapping("/join/idsearch")
	public ResponseEntity<?> postJoinIdSearch(@RequestBody Member member) {

		String username = member.getUsername();
		
		if(joinService.postJoinIdSearch(username)) {
			System.out.println("[경고/오류] : [JoinController][이미 사용 중인 아이디입니다.]" + "\n");
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		System.out.println("[성공] : [JoinController] 사용할 수 있는 아이디 검색^^ \n");
        return ResponseEntity.status(HttpStatus.OK).build();
  
    	
	    
	}
	 
	
	// POST /api/public/login 요청 → Filter가 잡아서 처리 → JWT 토큰 응답
	// Controller에는 login 메서드가 없어야 정상!
	
	// UsernamePasswordAuthenticationFilter의 기본 URL은 /login이기 때문에
	// /api/public/login도 작동하게 setFilterProcessesUrl 설정 필요!
	
//	@PostMapping("/login") 
//	public String postLogin(@RequestBody Member member) {
//		String username = member.getUsername();
//        UserDetails userDetails = memberService.loadUserByUsername(username);
//		return userDetails.getUsername();
//	}
}
