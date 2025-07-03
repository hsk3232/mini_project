package edu.pnu.dto.goods;

import edu.pnu.domain.ImgAdress;
import edu.pnu.domain.QnA;
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

public class QnAListDTO {
	private Long qaid;
    private String imageUrl;
    private String productName;
    private String createdat;
    private String imgname;
    private String question;
    private boolean remain;
    private String username;
    
    public static QnAListDTO fromEntity(QnA q) {
        String imageUrl = q.getGoods().getImgAdressList().stream()
            .filter(ImgAdress::isIsmain)            // ismain == true 필터
            .map(ImgAdress::getImgUrl)              // 이미지 URL 추출
            .findFirst()
            .orElse(null);                          // 없으면 null 반환

        return QnAListDTO.builder()
        	.qaid(q.getQaid())
            .imageUrl(imageUrl)
            .productName(q.getGoods().getProductName())
            .createdat(q.getCreatedAt().toLocalDate().toString())
            .imgname(q.getGoods().getImgname())
            .question(q.getQuestion())
            .remain(q.isRemain())
            .build();
    }
    
    public static QnAListDTO fromEntity(String username, QnA q) {
        String imageUrl = q.getGoods().getImgAdressList().stream()
            .filter(ImgAdress::isIsmain)            // ismain == true 필터
            .map(ImgAdress::getImgUrl)              // 이미지 URL 추출
            .findFirst()
            .orElse(null);                          // 없으면 null 반환

        return QnAListDTO.builder()
        	.qaid(q.getQaid())
        	.username(q.getMember().getUsername())
            .imageUrl(imageUrl)
            .productName(q.getGoods().getProductName())
            .createdat(q.getCreatedAt().toLocalDate().toString())
            .imgname(q.getGoods().getImgname())
            .question(q.getQuestion())
            .remain(q.isRemain())
            .build();
    }
}
