package edu.pnu.service.everyone;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Goods;
import edu.pnu.dto.goods.GoodsDTO;
import edu.pnu.dto.goods.GoodsOptionDTO;
import edu.pnu.dto.goods.ImgAdressDTO;
import edu.pnu.persistence.GoodsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsDetailService {
	
	private final GoodsRepository goodsRepo;
	
    public GoodsDTO getGoodsDetail(String imgname) {
    	// 1. 상품 정보 조회
    	System.out.println("[진입] : [조회할 상품 번호 " + imgname + "]");
        Goods goods = goodsRepo.findById(imgname)
            .orElseThrow(() -> new IllegalArgumentException("[오류/ 경고] : 해당 상품이 없습니다 [" + imgname + "]"));
        
        // 2. 옵션/이미지 등 추가 데이터 가공 (필요하면!)
        // 예시: 옵션, 이미지 리스트 DTO로 변환
        List<GoodsOptionDTO> options = goods.getGoodsOptionList()
            .stream().map(GoodsOptionDTO::fromEntity).toList();
        List<ImgAdressDTO> imglist = goods.getImgAdressList()
            .stream().map(ImgAdressDTO::fromEntity).toList();

        // 3. DTO 조립
        return GoodsDTO.fromEntity(goods, goods.getDescription(), options, imglist);
    }
}