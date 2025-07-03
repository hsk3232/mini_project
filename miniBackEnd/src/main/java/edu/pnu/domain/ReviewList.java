package edu.pnu.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Builder
@Entity
@Table(name="reviewlist")
public class ReviewList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderid")
	private OrderList orderList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username") // FK 이름
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imgname", referencedColumnName = "imgname")
	private Goods goods;

	private String reviewtext;
	private int rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "optionid")
	private GoodsOption goodsOption;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private LocalDateTime createdat;

	@Column(columnDefinition = "TINYINT(1)")
	@Builder.Default
	private boolean remain = true;

}
