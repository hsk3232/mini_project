package edu.pnu.dto.goods;

import java.util.Collections;
import java.util.List;

import edu.pnu.domain.Goods;
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
        Goods goods = q.getGoods();

        // goods가 null일 수 있으니 안전하게 처리!
        String imageUrl = null;
        String productName = null;
        String imgname = null;
        List<ImgAdress> imgAdressList = Collections.emptyList();

        if (goods != null) {
            imageUrl = goods.getImgAdressList().stream()
                .filter(ImgAdress::isIsmain)
                .map(ImgAdress::getImgUrl)
                .findFirst()
                .orElse(null);
            productName = goods != null ? goods.getProductName() : "상품 없음";
            imgname = goods.getImgname();
            imgAdressList = goods.getImgAdressList();
        }

        return QnAListDTO.builder()
            .qaid(q.getQaid())
            .imageUrl(imageUrl)
            .productName(productName)
            .createdat(q.getCreatedAt().toLocalDate().toString())
            .imgname(imgname)
            .question(q.getQuestion())
            .remain(q.isRemain())
            .username(q.getMember() != null ? q.getMember().getUsername() : null)
            .build();
    }
    
    public static QnAListDTO fromEntity(String username, QnA q) {
        Goods goods = q.getGoods();

        String imageUrl = null;
        String productName = null;
        String imgname = null;
        if (goods != null) {
            imageUrl = goods.getImgAdressList().stream()
                .filter(ImgAdress::isIsmain)
                .map(ImgAdress::getImgUrl)
                .findFirst()
                .orElse(null);
            productName = goods != null ? goods.getProductName() : "상품 없음";
            imgname = goods.getImgname();
        }

        return QnAListDTO.builder()
            .qaid(q.getQaid())
            .username(username != null ? username : (q.getMember() != null ? q.getMember().getUsername() : null))
            .imageUrl(imageUrl)
            .productName(productName)
            .createdat(q.getCreatedAt().toLocalDate().toString())
            .imgname(imgname)
            .question(q.getQuestion())
            .remain(q.isRemain())
            .build();
    }
}
