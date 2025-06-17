package edu.pnu.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Goods {
	@Id
	private String fullcode;		// 전체코드
	private String imgname;			// 이미지명
	
	private String main;			// 대분류
	private String mid;				// 중분류
	private String detail;			// 소분류
	private String gender;			// 성별
	private String color;			// 색상
	private String print;			// 무늬
	
	@Column(name = "productname")
	private String productName;		// 상품명
	
	private int price;				// 가격
	private String maincode; 		// 대분류 코드
	private String midcode; 		// 중분류 코드
	private String detailcode; 		// 소분류 코드
	private String gendercode; 		// 성별 코드
	private String colorcode; 		// 색상코드
	private String categorycode; 	// 카테고리 코드
	private String uniquecode; 		// 유니크 코드
	private int viewcount; 			// 제품 조회수
	private LocalDate registerdate; // 상품 등록일
	private String size; 				// 사이즈
	private int stock; 				// 제고
	private String description; 	// 상품 설명
	

}
