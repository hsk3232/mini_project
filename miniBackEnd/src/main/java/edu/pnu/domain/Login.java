package edu.pnu.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Login {
	
	@Id
	private String user_id;
	
	@UpdateTimestamp //수정일 자동 기록
	private LocalDateTime last_login; //마지막 로그인 시간
	
	@UpdateTimestamp
	private LocalDateTime last_logout; //마지막 로그아웃 시간
	
	private boolean login_success; //로그인 시도 성공여부
	
}
