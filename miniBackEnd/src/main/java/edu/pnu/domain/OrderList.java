package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity	
@Table(name="orderlist")
public class OrderList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private Member member;
	private LocalDateTime orderdate;
	
	private String orderstatus;
	private int total;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "optionid", referencedColumnName = "optionid")
	private GoodsOption goodsOption;
	
	private int quantity;
	private String name;
	private String zip;
	private String address1;
	private String address2;
	private String phone;
	private String payment;
	
	 @OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Review> review = new ArrayList<>();
}
