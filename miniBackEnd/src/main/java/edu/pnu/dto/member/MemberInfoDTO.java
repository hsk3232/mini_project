package edu.pnu.dto.member;

import java.time.LocalDate;

import edu.pnu.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDTO {
	private String nickname;
	private LocalDate birth;
	private String gender;
	private String phone;

	private String email;
	
	public static MemberInfoDTO fromEntity(Member member) {
		return MemberInfoDTO.builder()
				.nickname(member.getNickname())
		        .birth(member.getBirth())
		        .gender(member.getGender())
		        .phone(member.getPhone())
		        .email(member.getEmail())
		        .build();
	
	}
}
