package edu.pnu.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.dto.Orders.OrderAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//예) MemberResponseDTO.java (회원 정보 응답용 DTO)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO {
	private String nickname;
	private String username;
	private LocalDate birth;
	private String gender;
	private String phone;
	private LocalDateTime createdat;
	private OrderAddressDTO address;
	private String email;
 // 비밀번호 등 민감 정보는 포함하지 않음
	
	
	
 
 public static MemberResponseDTO fromEntity(Member member) {
	    return MemberResponseDTO.builder()
	    	.nickname(member.getNickname())
	        .username(member.getUsername())
	        .birth(member.getBirth())
	        .gender(member.getGender())
	        .phone(member.getPhone())
	        .createdat(member.getCreatedAt())
	        .address(
	        	    member.getAddresses().stream()
	        	        .filter(OrderAddress::isMain)           // isMain == true
	        	        .findFirst()
	        	        .map(OrderAddressDTO::fromEntity)       // DTO로 변환
	        	        .orElse(null)                           // 없으면 null
	        	)
	        .email(member.getEmail())
	        .build();
	}

}
