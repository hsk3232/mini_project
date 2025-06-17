package edu.pnu.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pnu.domain.Goods;
import edu.pnu.dto.goods.GoodsDTO;

public class GoodsImgUtil {
	
	//중복된 imgname을 제거 + Goods 객체 -> GoodsDTO로 변환하는 Util
    public static List<GoodsDTO> removeDuplicateByImgname(List<Goods> goodsList, int limit) {
        Set<String> seen = new HashSet<>();
        List<GoodsDTO> result = new ArrayList<>();

        for (Goods g : goodsList) {
            if (seen.add(g.getImgname())) { // Set은 중복이면 false 반환
                result.add(GoodsDTO.fromEntity(g));
                if (result.size() == limit) break;
            }
        }

        return result;
    }
	
}
