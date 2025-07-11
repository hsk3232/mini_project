package edu.pnu.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {

    @Id //PK column
    private String username;

    @Column(nullable = false) // not null 설정
    private String password; //비밀번호

    private String nickname; //가입자 이름

    @Enumerated(EnumType.STRING) //enum 타입 삽입
    private Role role; //권한

    private String gender; //성별
    
    private LocalDate birth; //생일

    private String email; //e-Mail 주소
    
    private String phone; //전화번호

    @CreationTimestamp
    @Column(updatable = false, name="createdat")
    private LocalDateTime createdAt; //가입일

    @UpdateTimestamp // 수정일 자동 기록
    @Column(name="loginat")
    private LocalDateTime loginAt; //마지막 로그인 일
    
    // serchHistory와 1:n mapping
    // cascade = CascadeType.ALL, orphanRemoval = true → 회원 삭제 시, 해당 회원의 검색기록도 모두 삭제
    @ToString.Exclude // 순환참조의 경우 사용
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchHistory> searchHistory = new ArrayList<>();
 
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<WishList> wishLists = new ArrayList<>();
    
	//user1명이 여러 상품에 리뷰를 남길 수 있음.
    @Builder.Default
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewList> review = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	@Builder.Default
	private List<QnA> qna = new ArrayList<>();
    
	@OneToMany(mappedBy = "member")
	private List<OrderList> orderLists;
	
	@Builder.Default
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderAddress> addresses = new ArrayList<>();
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Member member = (Member) o;
	    return username != null && username.equals(member.username);
	}

	@Override
	public int hashCode() {
	    return username != null ? username.hashCode() : 0;
	}
}
