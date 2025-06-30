package edu.pnu.dto.orders;

import java.time.LocalDateTime;

import edu.pnu.domain.ImgAdress;
import edu.pnu.domain.ReviewList;
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
public class ReviewListDTO {
	private Long reviewid;
	private Long orderid;
	private String username;
	private String imgname;
	private String productName;
	private String optionid;
    private String imgUrl;
    
    
	private String reviewtext;
	private int rating;
	private LocalDateTime createdate;
	private boolean remain;

	public static ReviewListDTO fromEntity(ReviewList r) {
	    String imgUrl = r.getGoods().getImgAdressList().stream()
	        .filter(ImgAdress::isIsmain)
	        .map(ImgAdress::getImgUrl)
	        .findFirst()
	        .orElse(null);

	    return ReviewListDTO.builder()
	        .reviewid(r.getReviewid())
	        .orderid(r.getOrderList().getOrderid())    // 단일 orderid
	        .username(r.getMember().getUsername())
	        .imgname(r.getGoods().getImgname())
	        .productName(r.getGoods().getProductName())
	        .optionid(r.getGoodsOption().getOptionid())
	        .imgUrl(imgUrl)
	        .reviewtext(r.getReviewtext())
	        .rating(r.getRating())
	        .createdate(r.getCreatedat())
	        .remain(r.isRemain())
	        .build();
	}
}
