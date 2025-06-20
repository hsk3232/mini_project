package edu.pnu.dto.goods;

import java.time.LocalDate;
import java.util.List;

import edu.pnu.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {
	private String imgname;
	private String productName;
    private int price;
    private String color;
    private String gender;
    private String print;
    private String viewcount;
    private LocalDate registerdate; // 상품 등록일

	private String description;
	private List<GoodsOptionDTO> options; // 사이즈/재고/풀코드
	private List<ImgAdressDTO> imglist;
	
	// JSON은 여기서 담은 정보대로 나감.
    public static GoodsDTO fromEntity(Goods goods) {
    	return GoodsDTO.builder()
    	        .imgname(goods.getImgname())
    	        .productName(goods.getProductName())
    	        .price(goods.getPrice())
    	        .color(goods.getColor())
    	        .gender(goods.getGender())
    	        .print(goods.getPrint())
    	        .registerdate(goods.getRegisterdate())
    	        .build();

    }
    
    public static GoodsDTO fromEntity(Goods g, String description, List<GoodsOptionDTO> options, List<ImgAdressDTO> imglist) {
    	return GoodsDTO.builder()
    			.imgname(g.getImgname())
                .productName(g.getProductName())
                .price(g.getPrice())
                .color(g.getColor())
                .gender(g.getGender())
                .print(g.getPrint())
                .description(description)
                .options(options)
                .imglist(imglist)
                .build();
    }
    
   

}
