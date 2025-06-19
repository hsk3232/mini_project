package edu.pnu.domain;

import java.time.LocalDateTime;

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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="searchhistory")
public class SearchHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private String keyword;
	
	@Column(name="searchedat")
	private LocalDateTime searchedAt;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private Member member;
}
