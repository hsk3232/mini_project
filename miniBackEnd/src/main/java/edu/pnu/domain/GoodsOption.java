package edu.pnu.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Entity
@Builder

@Table(name="Goodsoption")
public class GoodsOption {
	
	@Id
    @Column(length = 100)
    private String optionid; // 옵션 고유코드(PK)

    private String fullcode;
    private String size;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY) // FK
    @JoinColumn(name = "imgname", referencedColumnName = "imgname")
    private Goods goods;

    // 재고 감소 로직
    public void decreaseStock(int quantity) {
        if (this.stock == null || this.stock < quantity) {
            throw new IllegalArgumentException("[오류] 재고 부족: " + this.optionid);
        }
        this.stock -= quantity;
    }

    // (필요하면) 재고 증가
    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("[오류] 잘못된 수량: " + quantity);
        }
        this.stock += quantity;
    }
    
    @OneToMany(mappedBy = "goodsOption", fetch = FetchType.LAZY)
    private List<OrderItem> orderList;
}
