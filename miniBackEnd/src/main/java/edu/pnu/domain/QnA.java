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
@Table(name="qna")
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long qaid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private Member member;
	
	
	// N:1 (imgname으로 goods와 연결)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgname", referencedColumnName = "imgname")
    private Goods goods;
	
	private String question;
	
	private String answer;
	
	@CreationTimestamp
    @Column(updatable = false, name="createdat")
    private LocalDateTime createdAt; //질문일
	
	@CreationTimestamp
    @Column(updatable = false, name="answeredat")
    private LocalDateTime answeredAt; //대답일

	
	@Column(columnDefinition = "TINYINT(1)")
	@Builder.Default
	private boolean remain = true;
	
	private String answerusername;
	
	
	
}
