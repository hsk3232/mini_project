package edu.pnu.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Sign {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 생성 No.
    private Long signId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 외래키 join
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private boolean signedOut; //탈퇴 여부

    @UpdateTimestamp
    private LocalDateTime signedOutAt;//탈퇴 시간
		
}
