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
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "imgadress")
public class ImgAdress {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long seq;  // 여러 이미지 저장을 위해 auto-increment PK

	    private String imgname; // FK
	    @Column(name="imgurl")
	    private String imgUrl;
	    private boolean ismain;

	    // N:1 (imgname으로 goods와 연결)
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "imgname", referencedColumnName = "imgname", insertable = false, updatable = false)
	    private Goods goods;
}
