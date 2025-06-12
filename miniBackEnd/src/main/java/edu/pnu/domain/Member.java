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
public class Member {

    @Id //PK column
    private String username;

    @Column(nullable = false) // not null 설정
    private String password; //비밀번호

    private String nickname; //가입자 이름

    @Enumerated(EnumType.STRING) //enum 타입 삽입
    private Role role; //권한

    private String userGender; //성별

    private LocalDate birth; //생일

    private String email; //e-Mail 주소
    
    private String phone; //전화번호

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; //가입일

    @UpdateTimestamp // 수정일 자동 기록
    private LocalDateTime loginAt; //마지막 로그인 일
    
}
