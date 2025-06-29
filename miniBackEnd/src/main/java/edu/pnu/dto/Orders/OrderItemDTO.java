package edu.pnu.dto.Orders;

import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private String optionid;    // 어떤 옵션인지 (GoodsOption PK)
    private String imgname;
    private String productName;
    private String imgUrl;
    private int quantity;       // 몇 개 샀는지
    private int price;          // 개당 가격 또는 총액 (실무에 따라)
   
    
    
    // 엔티티 -> DTO 변환 메서드
    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .optionid(orderItem.getGoodsOption().getOptionid())
                .imgname(orderItem.getGoodsOption().getGoods().getImgname())
                .productName(orderItem.getGoodsOption().getGoods().getProductName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .imgUrl(orderItem.getGoodsOption().getGoods().getImgAdressList().stream()
                        .filter(img -> img.isIsmain()) // ismain이 true인 것만
                        .filter(img -> img.getImgname()
                        					.equals(orderItem.getGoodsOption()
                        							.getGoods().getImgname())) // imgname이 같은 것만
                        .findFirst()
                        .map(img -> img.getImgUrl()) // imgUrl만 추출
                        .orElse(null)) // 없으면 null
                
                .build();
                
                
    }
    
    public static OrderItem toEntity(OrderItemDTO dto, OrderList order, GoodsOption option) {
        return OrderItem.builder()
                .orderList(order)
                .goodsOption(option)
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }
    

}