package edu.pnu.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString @Builder 
@NoArgsConstructor
@AllArgsConstructor

public class MemberSignDTO {
    private String username;
    private String password; //비밀번호
    private String nickname; //가입자 이름
    private Role role; //권한
    private String gender; //성별
    private LocalDate birth; //생일
    private String email; //e-Mail 주소
    private String phone; //전화번호
    private LocalDateTime createdAt; //가입일
    
    // DTO → Entity 변환 메서드
    public Member toEntity() {
        return Member.builder()
            .username(username)
            .password(password)
            .nickname(nickname)
            .gender(gender)
            .birth(birth)
            .email(email)
            .phone(phone)
            .role(Role.ROLE_MEMBER) // 가입자는 무조건 member로
            .build();
    	
    	
    }
}
