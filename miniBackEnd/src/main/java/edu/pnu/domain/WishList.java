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
@Table(name="wishlist")
public class WishList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgname", referencedColumnName = "imgname", insertable = false, updatable = false)
    private Goods goods;
	
	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private LocalDateTime createdat;
}
