package edu.pnu.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id //PK column
    private String userId;

    @Column(nullable = false) // not null 설정
    private String password; //비밀번호

    private String name; //가입자 이름

    @Enumerated(EnumType.STRING) //enum 타입 삽입
    private Role role; //권한

    @Enumerated(EnumType.STRING)
    private Gender gender; //성별

    private LocalDate birth; //생일

    private String email; //e-Mail 주소
    
    private String phone; //전화번호

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; //가입일

    @UpdateTimestamp // 수정일 자동 기록
    private LocalDateTime updatedAt; //개인정보 수정일
    
	//User 객체에서만 쓰이는 내부 enum
	public enum Gender { 
		MALE, FEMALE
	}
	
}
