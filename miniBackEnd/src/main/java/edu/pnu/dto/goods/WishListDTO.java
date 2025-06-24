package edu.pnu.dto.goods;

import edu.pnu.domain.ImgAdress;
import edu.pnu.domain.WishList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class WishListDTO {
    private String imageUrl;
    private String productName;
    private String createdat;
    private String imgname;
    private boolean remain;
    
    public static WishListDTO fromEntity(WishList wish) {
        String imageUrl = wish.getGoods().getImgAdressList().stream()
            .filter(ImgAdress::isIsmain)            // ismain == true 필터
            .map(ImgAdress::getImgUrl)              // 이미지 URL 추출
            .findFirst()
            .orElse(null);                          // 없으면 null 반환

        return WishListDTO.builder()
            .imageUrl(imageUrl)
            .productName(wish.getGoods().getProductName())
            .createdat(wish.getCreatedat().toLocalDate().toString())
            .imgname(wish.getGoods().getImgname())
            .remain(wish.isRemain())
            .build();
    }
    

}