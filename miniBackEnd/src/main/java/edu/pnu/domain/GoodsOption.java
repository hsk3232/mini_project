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

    private String imgname; // FK

    private String fullcode;
    private String size;
    private Integer stock;

    // N:1 (imgname으로 goods와 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgname", referencedColumnName = "imgname", insertable = false, updatable = false)
    private Goods goods;
    
    @OneToMany(mappedBy = "goodsOption", fetch = FetchType.LAZY)
    private List<OrderItem> orderList;
}
