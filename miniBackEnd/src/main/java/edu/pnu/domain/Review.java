package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderid")
	private OrderList orderList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")  // FK 이름
	private Member member;
	
	private String imgname;
	private String optionid;
	private String reviewtext;
	private int rating;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private LocalDateTime createdat;
	
	

}
