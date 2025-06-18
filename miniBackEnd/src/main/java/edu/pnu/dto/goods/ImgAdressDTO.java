package edu.pnu.dto.goods;

import edu.pnu.domain.ImgAdress;
import jakarta.persistence.Entity;
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
@Builder
public class ImgAdressDTO {
	private String imgname;
	private String imgUrl;
    private boolean ismain;
    
    // 엔티티 -> DTO 변환 
    public static ImgAdressDTO fromEntity(ImgAdress img) {
        return ImgAdressDTO.builder()
                .imgname(img.getImgname())
                .imgUrl(img.getImgUrl()) // 이미지 주소 합치기
                .ismain(img.isIsmain())
                .build();
    }
}
