package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.dto.goods.AdGoodsDTO;
import edu.pnu.dto.goods.GoodsDTO;
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
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="orderlist")
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Member member;

    private LocalDateTime orderdate;
    private String orderstatus;
    private int total;
    
    private String payment;

    @OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // 리뷰도 필요하면 그대로 둬도 됨
    @OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private OrderAddress address;
}
