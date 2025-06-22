package edu.pnu.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name="goods")
public class Goods {
	@Id
	private String imgname;			// 이미지명
	private String fullcode;		// 전체코드

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

	private int viewcount; 			// 제품 조회수
	private LocalDate registerdate; // 상품 등록일
	private String description; 	// 상품 설명
	
	//fetch = FetchType.LAZY 바로 가져오지 말고 필요할 때 가져와라
	// 1:N 옵션
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    private List<GoodsOption> goodsOptionList;

    // 1:N 이미지(여러 장일 경우)
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    private List<ImgAdress> imgAdressList;

}
