package edu.pnu.dto.cart;

import java.util.List;
import java.util.stream.Collectors;

import edu.pnu.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String username; // 소유 회원의 username (혹은 nickname, email 등도 추가 가능)
    private List<CartItemDTO> items; // 장바구니에 담긴 상품 옵션 리스트

    // Entity → DTO 변환 메서드
    public static CartDTO fromEntity(String username) {
        return CartDTO.builder()
                .username(getMember().getUsername())
                .items(
                    cart.getCartItems().stream()
                        .map(CartItemDTO::fromEntity)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
