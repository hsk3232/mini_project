package edu.pnu.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//예) MemberResponseDTO.java (회원 정보 응답용 DTO)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO {
 private String username;
 private String nickname;
 private String email;
 private String role;
 // 비밀번호 등 민감 정보는 포함하지 않음
}
