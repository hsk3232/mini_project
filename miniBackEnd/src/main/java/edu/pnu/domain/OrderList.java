package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderlist")
public class OrderList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private Member member;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime orderdate;
	private int total;
	private String payment;
	private String orderstatus;

	@OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default // ✅ @Builder와 함께 초기화가 되도록!
	private List<OrderItem> items = new ArrayList<>();

	// 리뷰도 필요하면 그대로 둬도 됨
	@Builder.Default
	@OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewList> review = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addressid") // ✅ 이렇게 변경
	private OrderAddress address;

	public void addItem(OrderItem item) {
		this.items.add(item); // List에 추가
		item.setOrderList(this); // 양방향 관계 유지
	}
}
