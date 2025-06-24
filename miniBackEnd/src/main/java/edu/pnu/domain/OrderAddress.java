package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name= "orderaddress")
public class OrderAddress {
	
	// 배송지 주소 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="addressid")
    private Long addressId;
    
    // 해당 주소가 어떤 회원의 주소인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Member member;

    private String name; //택배 받는 분 이름
    private String zip;
    private String address1;
    private String address2;
    private String phone;
    private boolean main; // true = 기본 주소
    @Column(name = "deleteaddr")
    private boolean deleteAddr; // true = 삭제


}
